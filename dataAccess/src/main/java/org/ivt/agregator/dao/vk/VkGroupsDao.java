package org.ivt.agregator.dao.vk;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.ivt.agregator.dao.ExternalEventDao;
import org.ivt.agregator.dao.exception.VkDaoException;
import org.ivt.agregator.integration.vk.VkAuthHelper;
import org.ivt.agregator.integration.vk.VkGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VkGroupsDao implements ExternalEventDao<VkGroup> {

    private static Logger logger = Logger.getLogger(VkGroupsDao.class.getName());
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
        logger.info(String.format("Получение событий из ВК text %s, cityId %s", text, cityId));
        try {
            //вызов хранимой процедуры приложения VK
            JsonElement jsonElement = vk.execute().storageFunction(authHelper.getUserActor(), "getGroups").
                    unsafeParam("text", text).unsafeParam("cityId", cityId).execute();
            return GSON.fromJson(jsonElement, TYPE);
        } catch (ClientException | ApiException e) {
            logger.warning("Ошибка при получении событий из ВК" + e.getMessage());
            throw new VkDaoException(e);
        }
    }
}
