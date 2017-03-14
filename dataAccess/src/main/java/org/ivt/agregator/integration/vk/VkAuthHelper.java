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
    public static final String REDIRECT_URI = "http://185.159.130.67:8080/api/v1/vk/util/setCode";

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

    public void invalidateActor() {
        userActor = null;
        resetToken();
    }

    private void resetToken() {
        Parameter parameter = new Parameter();
        parameter.setKey(Parameter.VK_TOKEN);
        parameterDao.save(parameter);
    }

    private void createUserActor() throws ApiException, ClientException {
        Parameter tokenParameter = parameterDao.get(Parameter.VK_TOKEN);
        String token;
        if (tokenParameter == null || tokenParameter.getValue() == null) {
            token = getTokenByVkCode();
            saveToken(token);
        } else {
            token = tokenParameter.getValue();
        }
        userActor = new UserActor(APP_ID, token);
    }

    private void saveToken(String token) {
        Parameter tokenParam = new Parameter();
        tokenParam.setKey(Parameter.VK_TOKEN);
        tokenParam.setValue(token);
        parameterDao.save(tokenParam);
    }

    private String getTokenByVkCode() throws ApiException, ClientException {
        String code = parameterDao.get(Parameter.VK_CODE).getValue();
        if (code == null) {
            throw new IllegalStateException("Параметр Код vk не лежит в БД");
        }
        UserAuthResponse authResponse = vk.oauth().
                userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code).execute();
        return authResponse.getAccessToken();
    }
}
