package org.ivt.agregator.service;

import org.ivt.agregator.entity.Event;

import java.util.List;

public interface EventService {

    List<Event> getAll(int offset, int count);

    void rateUp(Long eventId, float rateStep);

    void rateDown(Long eventId, float rateStep);

    Event get(Long eventId);
}
