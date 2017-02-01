package org.ivt.agregator.converters;

import com.vk.api.sdk.objects.groups.GroupFull;
import org.ivt.agregator.entity.Address;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.integration.ExtSystem;
import org.ivt.agregator.integration.vk.VkGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventConverter {

    public List<Event> convert(List<VkGroup> groups) {
        ArrayList<Event> events = new ArrayList<>();
        for (VkGroup group : groups) {
            events.add(convert(group));
        }
        return events;
    }

    public Event convert(VkGroup group) {
        Event event = new Event();
        event.setExtId(group.getId());
        event.setName(group.getName());
        event.setDescription(group.getDescription());
        event.setExtSystem(ExtSystem.VK);
        if (group.getStartDate() != null) {
            event.setBeginTime(new Date(group.getStartDate()));
        }
        if (group.getFinishDate() != null) {
            event.setEndTime(new Date(group.getFinishDate()));
        }
        event.setAddress(new Address());
        if(group.getCity() != null) {
            event.getAddress().setCity(group.getCity().getTitle());
        }
        event.setImageUrl(group.getPhoto200());

        return event;
    }
}
