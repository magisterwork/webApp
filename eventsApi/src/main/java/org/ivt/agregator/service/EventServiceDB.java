package org.ivt.agregator.service;

import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.entity.Event;

import java.util.List;

public class EventServiceDB implements EventService {

    private EventDao eventDao;

    public EventServiceDB(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public List<Event> get(int offset, int count) {
        return eventDao.get(count, offset);
    }
}
