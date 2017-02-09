package org.ivt.agregator.dao.vk;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.ivt.agregator.dao.ExternalEventDao;
import org.ivt.agregator.integration.vk.VkExecutor;
import org.ivt.agregator.integration.vk.entity.VkGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class VkGroupsDao implements ExternalEventDao<VkGroup> {

    private static Logger logger = Logger.getLogger(VkGroupsDao.class.getName());
    private VkExecutor vkExecutor;
    public static final Gson GSON = new Gson();
    public static final Type TYPE = new TypeToken<ArrayList<VkGroup>>() {
    }.getType();

    public VkGroupsDao(VkExecutor vkExecutor) {
        this.vkExecutor = vkExecutor;
    }

    public List<VkGroup> get(String text, int cityId) {
        logger.info(String.format("Получение событий из ВК text %s, cityId %s", text, cityId));
        HashMap<String, Object> params = new HashMap<>();
        params.put("text", text);
        params.put("cityId", cityId);
        JsonElement jsonElement = vkExecutor.executeStorageFunction("getGroups", params);
        return GSON.fromJson(jsonElement, TYPE);
    }
}
