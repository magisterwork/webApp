package org.ivt.agregator.vkutils;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.ivt.agregator.dao.ParameterDao;
import org.ivt.agregator.entity.Parameter;

public class VkAuthHelper {

    private static final Integer APP_ID = 5687850;
    private static final String CLIENT_SECRET = "3wgJNMnycr4eajTOMosV";
    private ParameterDao parameterDao;

    public VkAuthHelper(ParameterDao parameterDao) {
        this.parameterDao = parameterDao;
    }

    public void getTransactionToken() throws ClientException, ApiException {
        String code = parameterDao.get(Parameter.VK_CODE).getValue();

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserAuthResponse authResponse = vk.oauth().
                userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, "", code).execute();

        UserActor actor = new UserActor(APP_ID, authResponse.getAccessToken());

        vk.groups().search(actor, "а").cityId(41).future(true);
    }
}
