package org.ivt.agregator.rest;

import org.ivt.agregator.entity.Event;
import org.ivt.agregator.service.EventService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/events")
public class EventsGettingController {

    private static Logger logger = Logger.getLogger(VkCodeController.class.getName());
    private EventService eventService;

    public EventsGettingController(EventService eventService) {
        this.eventService = eventService;
    }

    @GET
    @Path("/list")
    @Produces(APPLICATION_JSON)
    public List<Event> list(@QueryParam("offset")int offset, @QueryParam("count") int count) {
        List<Event> events = eventService.get(offset, count);
        logger.info(String.format("Отдаём %d запрошенных событий. Offset %d. Count %d",
                events.size(), offset, count));
        return events;
    }



}
