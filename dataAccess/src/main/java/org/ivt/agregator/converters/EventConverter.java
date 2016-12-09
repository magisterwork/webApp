package org.ivt.agregator.converters;

import com.vk.api.sdk.objects.groups.GroupFull;
import org.ivt.agregator.entity.Event;

import java.util.Date;

public class EventConverter {

    public Event convert(GroupFull group) {
        Event event = new Event();
        event.setExtId(group.getId());
        event.setBeginTime(new Date(group.getStartDate()));
        return event;
    }
}
