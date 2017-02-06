package org.ivt.agregator.rest;

import com.google.common.base.Strings;
import org.ivt.agregator.dao.ParameterDao;
import org.ivt.agregator.entity.Parameter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import java.util.logging.Logger;

import static org.ivt.agregator.entity.Parameter.VK_CODE;

@Path("/events/setCode")
public class VkCodeController {

    private ParameterDao parameterDao;
    private static Logger logger = Logger.getLogger(VkCodeController.class.getName());

    public VkCodeController(ParameterDao parameterDao) {
        this.parameterDao = parameterDao;
    }

    @GET
    public Response setupCode(@QueryParam("code") String code) {
        if (!Strings.isNullOrEmpty(code)) {
            Parameter parameter = new Parameter();
            parameter.setKey(VK_CODE);
            parameter.setValue(code);
            parameterDao.save(parameter);
            logger.info(String.format("Авторизационный код ВК установлен %s", code));
        }
        return Response.ok("Code is " + code).build();
    }
}
