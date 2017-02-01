package org.ivt.agregator.utils;

import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.entity.Event;

import java.util.ArrayList;
import java.util.List;

public class EventAddingFilterHardcodeImpl implements EventAddingFilter {

    private EventDao eventDao;

    public EventAddingFilterHardcodeImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public List<Event> filter(List<Event> source) {
        ArrayList<Event> events = new ArrayList<Event>();
        for (Event event : source) {
            if (shouldAdd(event)) {
                events.add(event);
            }
        }
        return events;
    }

    public boolean shouldAdd(Event event) {
        return eventExist(event);
    }

    private boolean eventExist(Event event) {
        Event stored = eventDao.getByExtId(event.getExtId(), event.getExtSystem());
        if (stored != null) {
            return false;
        }
        return true;
    }
}
