package org.ivt.agregator.dao;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiTooManyException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.GroupFull;
import org.ivt.agregator.dao.exception.VkDaoException;
import org.ivt.agregator.integration.vk.VkAuthHelper;

import java.util.ArrayList;
import java.util.List;

public class VkGroupsDao implements ExternalEventDao<GroupFull> {

    public static final String TYPE = "event";
    public static final int TIMEOUT = 1000;
    private VkAuthHelper authHelper;
    private VkApiClient vk;

    public VkGroupsDao(VkAuthHelper authHelper, VkApiClient vk) {
        this.authHelper = authHelper;
        this.vk = vk;
    }

    public List<GroupFull> get(String text, int cityId, int count, int offset) {
        try {
            return getFullGroups(text, cityId, count, offset);
        } catch (ClientException | ApiException e) {
            throw new VkDaoException(e);
        }
    }

    private List<GroupFull> getFullGroups(String text, int cityId, int count, int offset) throws ApiException, ClientException {
        List<Group> incompleteGroups = getIncompleteGroups(text, cityId, count, offset);
        List<GroupFull> fulls = new ArrayList<>(incompleteGroups.size());
        fillFullGroups(incompleteGroups, fulls);
        return fulls;
    }

    private List<Group> getIncompleteGroups(String text, int cityId, int count, int offset) throws ApiException, ClientException {
        try {
            return vk.groups().search(authHelper.getUserActor(), text).cityId(cityId).future(true).type(TYPE)
                    .count(count).offset(offset).execute().getItems();
        } catch (ApiTooManyException e) {
            try {
                wait(TIMEOUT);
                return getIncompleteGroups(text, cityId, count, offset);
            } catch (InterruptedException e1) {
                throw new RuntimeException("Что-то пошло не так..");
            }
        }
    }

    private void fillFullGroups(List<Group> incompleteGroups, List<GroupFull> fulls) throws ApiException, ClientException {
        for (Group group : incompleteGroups) {
            try {
                addFullGroup(fulls, group);
            } catch (ApiTooManyException e) {
                try {
                    wait(TIMEOUT);
                    addFullGroup(fulls, group);
                } catch (InterruptedException e1) {
                    throw new RuntimeException("Что-то пошло не так..");
                }
            }
        }
    }

    private void addFullGroup(List<GroupFull> fulls, Group group) throws ApiException, ClientException {
        List<GroupFull> getted = vk.groups().getById(authHelper.getUserActor()).groupId(group.getId()).execute();
        fulls.addAll(getted);
    }
}
