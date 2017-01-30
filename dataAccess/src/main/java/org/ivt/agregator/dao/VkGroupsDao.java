package org.ivt.agregator.dao;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ApiTooManyException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.queries.groups.GroupField;
import org.ivt.agregator.dao.exception.VkDaoException;
import org.ivt.agregator.integration.vk.VkAuthHelper;
import org.ivt.agregator.integration.vk.VkGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VkGroupsDao implements ExternalEventDao<VkGroup> {

    public static final int TIMEOUT = 1000;
    private final static int COUNT_PER_REQUEST = 20;
    private VkAuthHelper authHelper;
    private VkApiClient vk;
    public static final Gson GSON = new Gson();
    public static final Type TYPE = new TypeToken<ArrayList<VkGroup>>() {
    }.getType();

    public VkGroupsDao(VkAuthHelper authHelper, VkApiClient vk) {
        this.authHelper = authHelper;
        this.vk = vk;
    }

    public List<VkGroup> get(String text, int cityId) {
        try {
            //вызов хранимой процедуры приложения VK
            JsonElement jsonElement = vk.execute().storageFunction(authHelper.getUserActor(), "getGroups").
                    unsafeParam("text", text).unsafeParam("cityId", cityId).execute();
            return GSON.fromJson(jsonElement, TYPE);
        } catch (ClientException | ApiException e) {
            throw new VkDaoException(e);
        }
    }
}
