package org.ivt.agregator.utils;

import org.ivt.agregator.entity.Event;

import java.util.List;

public interface EventAddingFilter {

    List<Event> filter(List<Event> source);
}
