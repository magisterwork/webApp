package org.ivt.agregator.rest;

import org.ivt.agregator.dao.ParameterDao;
import org.ivt.agregator.entity.Parameter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import static org.ivt.agregator.entity.Parameter.*;

@Path("/events/setCode")
public class VkCodeController {

    private ParameterDao parameterDao;

    public VkCodeController(ParameterDao parameterDao) {
        this.parameterDao = parameterDao;
    }

    @GET
    public Response setupCode(@QueryParam("code") String code) {
        if (code != null && !"".equals(code)) {
            Parameter parameter = new Parameter();
            parameter.setKey(VK_CODE);
            parameter.setValue(code);
            parameterDao.save(parameter);
        }
        return Response.ok().build();
    }
}
