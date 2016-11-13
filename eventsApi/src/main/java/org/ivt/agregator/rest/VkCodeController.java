package org.ivt.agregator.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/events/setCode")
public class VkCodeController {

    @GET
    public Response setupCode(@QueryParam("code") String code) {

        return Response.ok().build();
    }
}
