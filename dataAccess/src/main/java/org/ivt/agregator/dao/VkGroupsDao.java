package org.ivt.agregator.dao;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.GroupFull;
import org.ivt.agregator.dao.exception.VkDaoException;
import org.ivt.agregator.integration.vk.VkAuthHelper;

import java.util.ArrayList;
import java.util.List;

public class VkGroupsDao implements ExternalEventDao<GroupFull> {

    private VkAuthHelper authHelper;
    private VkApiClient vk;

    public VkGroupsDao(VkAuthHelper authHelper, VkApiClient vk) {
        this.authHelper = authHelper;
        this.vk = vk;
    }

    public List<GroupFull> get(String text, int cityId) {
        try {
            return getFullGroups(text, cityId);
        } catch (ClientException | ApiException e) {
            throw new VkDaoException(e);
        }
    }

    private List<GroupFull> getFullGroups(String text, int cityId) throws ApiException, ClientException {
        List<Group> incompleteGroups = getIncompleteGroups(text, cityId);
        List<GroupFull> fulls = new ArrayList<>(incompleteGroups.size());
        fillFullGroups(incompleteGroups, fulls);
        return fulls;
    }

    private List<Group> getIncompleteGroups(String text, int cityId) throws ApiException, ClientException {
        return vk.groups().search(authHelper.getUserActor(), text).cityId(cityId).future(true).execute().getItems();
    }

    private void fillFullGroups(List<Group> incompleteGroups, List<GroupFull> fulls) throws ApiException, ClientException {
        for (Group group : incompleteGroups) {
            List<GroupFull> getted = vk.groups().getById(authHelper.getUserActor()).groupId(group.getId()).execute();
            fulls.addAll(getted);
        }
    }
}
