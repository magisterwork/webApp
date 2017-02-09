package org.ivt.agregator.dao.vk;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.vk.api.sdk.objects.photos.Photo;
import org.ivt.agregator.integration.vk.VkExecutor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VkFullImageDao {

    public static final String QUERY = "API.photos.get({'owner_id':'%s', 'album_id':'profile', 'count':'1'})";
    public static final Gson GSON = new Gson();
    public static final Type TYPE = new TypeToken<ArrayList<Photo>>() {
    }.getType();
    private VkExecutor vkExecutor;

    public VkFullImageDao(VkExecutor vkExecutor) {
        this.vkExecutor = vkExecutor;
    }

    public String getFullImageUrl(String ownerId) {
        JsonElement jsonElement = vkExecutor.executeRequest(String.format(QUERY, ownerId));
        List<Photo> photos = GSON.fromJson(jsonElement, TYPE);
        if (photos != null && !photos.isEmpty()) {
            Photo photo = photos.get(0);
            return photo.getPhoto604();
        } else {
            return null;
        }
    }
}
