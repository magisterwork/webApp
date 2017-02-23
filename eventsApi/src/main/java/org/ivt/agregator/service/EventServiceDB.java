package org.ivt.agregator.service;

import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.service.exception.EventNotFoundException;

import java.util.List;

public class EventServiceDB implements EventService {

    private EventDao eventDao;

    public EventServiceDB(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public List<Event> get(int offset, int count) {
        return eventDao.getFutureEvents(count, offset);
    }

    @Override
    public void rateUp(Long eventId, float rateStep) {
        Event event = eventDao.get(eventId);
        if (event == null) {
            throw new EventNotFoundException(eventId);
        }
        event.setRate(event.getRate() + rateStep);
        eventDao.save(event);
    }

    @Override
    public void rateDown(Long eventId, float rateStep) {
        Event event = eventDao.get(eventId);
        if (event == null) {
            throw new EventNotFoundException(eventId);
        }
        event.setRate(event.getRate() - rateStep);
        eventDao.save(event);
    }
}
