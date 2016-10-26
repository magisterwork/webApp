package org.ivt.agregator.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/get")
public class GetController {

    @POST
    public String get() {
        return "Hello world";
    }
}
