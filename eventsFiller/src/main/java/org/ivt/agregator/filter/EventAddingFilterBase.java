package org.ivt.agregator.filter;

import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.entity.Event;

import java.util.ArrayList;
import java.util.List;

public class EventAddingFilterBase implements AddingFilter<Event> {

    private EventDao eventDao;

    public EventAddingFilterBase(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public boolean shouldAdd(Event event) {
        return eventNotExist(event);
    }

    private boolean eventNotExist(Event event) {
        Event stored = eventDao.getByExtId(event.getExtId(), event.getExtSystem());
        return stored == null;
    }
}
