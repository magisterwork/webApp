package org.ivt.agregator.utils;

import org.ivt.agregator.entity.Event;

import java.util.List;

public class DummyEventAddingFilter implements EventAddingFilter {

    public List<Event> filter(List<Event> source) {
        return source;
    }
}
