package org.ivt.agregator.rest;

import com.google.common.base.Strings;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.ivt.agregator.dao.ParameterDao;
import org.ivt.agregator.entity.Parameter;
import org.ivt.agregator.integration.vk.VkAuthHelper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import java.util.logging.Logger;

import static org.ivt.agregator.entity.Parameter.VK_CODE;

@Path("/vk/util")
public class VkCodeController {

    private static Logger logger = Logger.getLogger(VkCodeController.class.getName());

    private ParameterDao parameterDao;
    private VkAuthHelper authHelper;

    public VkCodeController(ParameterDao parameterDao, VkAuthHelper authHelper) {
        this.parameterDao = parameterDao;
        this.authHelper = authHelper;
    }

    @GET
    @Path("setCode")
    public Response setupCode(@QueryParam("code") String code) {
        if (!Strings.isNullOrEmpty(code)) {
            Parameter parameter = new Parameter();
            parameter.setKey(VK_CODE);
            parameter.setValue(code);
            parameterDao.save(parameter);
            try {
                authHelper.getUserActor();
            } catch (ClientException | ApiException e) {
                logger.warning(e.toString());
            }
            logger.info(String.format("Авторизационный код ВК установлен %s", code));
        }
        return Response.ok("Code is " + code).build();
    }
}
