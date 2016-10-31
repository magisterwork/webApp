package org.ivt.agregator.vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.responses.SearchResponse;
import org.ivt.agregator.entity.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class VkEventsApi {

    public static ConcurrentHashMap<String, String> vkCodes = new ConcurrentHashMap<String, String>();

    public List<Event> get() {

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserAuthResponse response = null;
        try {
            response = vk.oauth().userAuthorizationCodeFlow(5687850, "3wgJNMnycr4eajTOMosV", "http://92.101.169.251:8080/rest/get", "90e64c6f659ea48bad").execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        Integer userId = response.getUserId();
        String accessToken = response.getAccessToken();
        UserActor actor = new UserActor(userId, accessToken);
        SearchResponse searchResponse = null;
        try {
            searchResponse = vk.groups().search(actor, "а").type("event").cityId(41).execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        List<Group> items = searchResponse.getItems();

        return new ArrayList();
    }
}
