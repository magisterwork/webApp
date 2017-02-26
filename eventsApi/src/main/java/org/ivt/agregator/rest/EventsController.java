package org.ivt.agregator.rest;

import org.apache.commons.lang3.Validate;
import org.ivt.agregator.dao.ParameterDao;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.entity.Parameter;
import org.ivt.agregator.rest.dto.EventRateRequest;
import org.ivt.agregator.rest.dto.WithTokenResponse;
import org.ivt.agregator.service.EventService;
import org.ivt.agregator.service.UserService;

import javax.ws.rs.*;
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.ivt.agregator.rest.dto.BaseResponse.*;

@Path("/events")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class EventsController {

    private static Logger LOGGER = Logger.getLogger(VkCodeController.class.getName());
    private EventService eventService;
    private UserService userService;
    private ParameterDao parameterDao;

    public EventsController(EventService eventService, UserService userService, ParameterDao parameterDao) {
        this.eventService = eventService;
        this.userService = userService;
        this.parameterDao = parameterDao;
    }

    @GET
    @Path("/list")
    public List<Event> list(@QueryParam("offset")int offset, @QueryParam("count") int count) {
        List<Event> events = eventService.getAll(offset, count);
        LOGGER.info(String.format("Отдаём %d запрошенных событий. Offset %d. Count %d",
                events.size(), offset, count));
        return events;
    }

    @GET
    @Path("/get")
    public Event get(@QueryParam("id") Long eventId) {
        try {
            Event event = eventService.get(eventId);
            LOGGER.info("Отдаем событие с id" + eventId);
            return event;
        } catch (Exception e) {
            LOGGER.warning("Ошибка при получении события с id " + eventId + e);
            return null;
        }
    }

    @POST
    @Path("/rateup")
    public WithTokenResponse eventRateUp(EventRateRequest request) {
        WithTokenResponse response = new WithTokenResponse();
        try {
            String newToken = userService.checkTokenWithUpdating(request.getToken());
            response.setToken(newToken);

            Long eventId = request.getEventId();
            Validate.notNull(eventId);
            LOGGER.info("+ событию " + eventId);
            Float step = Float.valueOf(parameterDao.get(Parameter.RATE_STEP).getValue());
            eventService.rateUp(eventId, step);
        } catch (Exception e) {
            LOGGER.warning("Ошибка обработки запроса" + e);
            response.setStatus(ERROR_STATUS);
        }
        return response;
    }

    @POST
    @Path("/ratedown")
    public WithTokenResponse eventRateDown(EventRateRequest request) {
        WithTokenResponse response = new WithTokenResponse();
        try {
            String newToken = userService.checkTokenWithUpdating(request.getToken());
            response.setToken(newToken);

            Long eventId = request.getEventId();
            Validate.notNull(eventId);
            LOGGER.info("- событию " + eventId);
            Float step = Float.valueOf(parameterDao.get(Parameter.RATE_STEP).getValue());
            eventService.rateDown(eventId, step);
        } catch (Exception e) {
            LOGGER.warning("Ошибка обработки запроса" + e);
            response.setStatus(ERROR_STATUS);
        }
        return response;
    }
}
