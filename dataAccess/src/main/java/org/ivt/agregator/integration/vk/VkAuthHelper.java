package org.ivt.agregator.integration.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.ivt.agregator.dao.ParameterDao;
import org.ivt.agregator.entity.Parameter;

public class VkAuthHelper {

    private static final Integer APP_ID = 5687850;
    private static final String CLIENT_SECRET = "3wgJNMnycr4eajTOMosV";

    private ParameterDao parameterDao;
    private VkApiClient vk;

    public VkAuthHelper(ParameterDao parameterDao, VkApiClient vk) {
        this.parameterDao = parameterDao;
        this.vk = vk;
    }

    public UserActor getUserActor() throws ClientException, ApiException {
        String code = parameterDao.get(Parameter.VK_CODE).getValue();
        UserAuthResponse authResponse = vk.oauth().
                userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, "", code).execute();
        return new UserActor(APP_ID, authResponse.getAccessToken());
    }
}
