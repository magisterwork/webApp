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
    public static final String REDIRECT_URI = "http://185.159.130.67:8080/eventsApi/events/setCode";

    private ParameterDao parameterDao;
    private VkApiClient vk;
    private UserActor userActor;

    public VkAuthHelper(ParameterDao parameterDao, VkApiClient vk) {
        this.parameterDao = parameterDao;
        this.vk = vk;
    }

    public UserActor getUserActor() throws ClientException, ApiException {
        if (userActor == null) {
            createUserActor();
        }
        return userActor;
    }

    public void resetActor() {
        userActor = null;
    }

    private void createUserActor() throws ApiException, ClientException {
        String code = parameterDao.get(Parameter.VK_CODE).getValue();
        if (code == null) {
            throw new IllegalStateException("Параметр Код vk не лежит в БД");
        }
        UserAuthResponse authResponse = vk.oauth().
                userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code).execute();
        userActor = new UserActor(APP_ID, authResponse.getAccessToken());
    }
}
