package org.ivt.agregator.integration.vk;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.execute.ExecuteStorageFunctionQuery;
import org.ivt.agregator.dao.exception.VkDaoException;

import java.util.Map;
import java.util.logging.Logger;

public class VkExecutor {

    private static Logger logger = Logger.getLogger(VkExecutor.class.getName());
    private VkApiClient client;
    private VkAuthHelper authHelper;

    public VkExecutor(VkApiClient client, VkAuthHelper authHelper) {
        this.client = client;
        this.authHelper = authHelper;
    }

    /**
     * Выполнить хранимую процедуру ВК
     * @param funcName имя хранимой процедуры
     * @param params мапа с параметрами процедуры. Ключ-имя параметра, Значение - значение параметра.
     * @return
     */
    public JsonElement executeStorageFunction(String funcName, Map<String, Object> params) {
        try {
            logger.info(String.format("Выполнение хранимой процедуры %s с параметрами %s", funcName, params));
            UserActor userActor = authHelper.getUserActor();
            ExecuteStorageFunctionQuery query = client.execute().storageFunction(userActor, funcName);
            for (String paramName : params.keySet()) {
                query = query.unsafeParam(paramName, params.get(paramName));
            }
            return query.execute();
        } catch (ClientException | ApiException e) {
            logger.severe(String.format("Ошибка при выполнении хранимой процедуры  %s с параметрами %s", funcName, params));
            throw new VkDaoException("Ошибка при выполнении хранимой процедуры", e);
        }
    }

    /**
     * Выполнить запрос ВК
     * @param query текст запроса
     * @return
     */
    public JsonElement executeRequest(String query) {
        logger.info(String.format("Выполнение запроса ВК %s", query));
        try {
            UserActor userActor = authHelper.getUserActor();
            return client.execute().code(userActor, query).execute();
        } catch (ClientException | ApiException e) {
            logger.severe(String.format("Ошибка выполнения запроса ВК %s", query));
            throw new VkDaoException("Ошибка при выполнении запроса ВК", e);
        }
    }
}
