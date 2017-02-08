package org.ivt.agregator.converters;

import com.vk.api.sdk.objects.base.Place;
import org.ivt.agregator.entity.Address;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.integration.ExtSystem;
import org.ivt.agregator.integration.vk.VkGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventConverter {

    public static final long MILLIS_IN_SECOND = 1000l;

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
        addStartDate(group, event);
        addFinishDate(group, event);
        addAddress(group, event);
        event.setImageUrl(group.getPhoto200());

        return event;
    }

    private void addAddress(VkGroup group, Event event) {
        Address address = new Address();
        Place place = group.getPlace();
        if(place != null) {
            address.setCity(place.getCity());
            address.setCountry(place.getCountry());
            address.setAddress(place.getAddress());
            address.setLatitude(Double.valueOf(place.getLatitude()));
            address.setLongitude(Double.valueOf(place.getLongitude()));
        }
        event.setAddress(address);
    }

    private void addFinishDate(VkGroup group, Event event) {
        if (group.getFinishDate() != null) {
            event.setEndTime(new Date(group.getFinishDate() * MILLIS_IN_SECOND));
        }
    }

    private void addStartDate(VkGroup group, Event event) {
        if (group.getStartDate() != null) {
            event.setBeginTime(new Date(group.getStartDate() * MILLIS_IN_SECOND));
        }
    }
}
