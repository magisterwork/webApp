package org.ivt.agregator.integration.vk.dao;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.vk.api.sdk.objects.photos.Photo;
import org.ivt.agregator.integration.vk.VkExecutor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VkFullImageDao {

    public static final String QUERY = "API.photos.find({\"owner_id\":-%s, " +
            "\"album_id\":\"profile\", \"count\":\"1\"});";
    public static final Gson GSON = new Gson();
    private VkExecutor vkExecutor;

    public VkFullImageDao(VkExecutor vkExecutor) {
        this.vkExecutor = vkExecutor;
    }

    public String getFullImageUrl(String ownerId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("ownerId", ownerId);
        JsonElement jsonElement = vkExecutor.executeStorageFunction("getPhoto", params);
        return GSON.fromJson(jsonElement, String.class);
    }
}
