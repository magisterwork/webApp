package org.ivt.agregator.rest;

import org.ivt.agregator.entity.Event;
import org.ivt.agregator.service.EventService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/events/api")
public class GetController {

    private EventService eventService;

    public GetController(EventService eventService) {
        this.eventService = eventService;
    }

    @GET
    @Path("/list")
    @Produces(APPLICATION_JSON)
    public List<Event> list(@QueryParam("offset")int offset, @QueryParam("count") int count) {
        return eventService.get(offset, count);
    }

}
