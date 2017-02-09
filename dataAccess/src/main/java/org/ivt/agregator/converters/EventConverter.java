package org.ivt.agregator.converters;

import org.ivt.agregator.entity.Place;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.integration.ExtSystem;
import org.ivt.agregator.integration.vk.VkGroup;

import java.util.Date;

public class EventConverter {

    public static final long MILLIS_IN_SECOND = 1000l;

    public Event convert(VkGroup group) {
        Event event = new Event();
        event.setExtId(group.getId());
        event.setName(group.getName());
        event.setDescription(group.getDescription());
        event.setExtSystem(ExtSystem.VK);
        addStartDate(group, event);
        addFinishDate(group, event);
        addAddress(group, event);
        event.setPreviewUrl(group.getPhoto200());

        return event;
    }

    private void addAddress(VkGroup group, Event event) {
        Place place = new Place();
        com.vk.api.sdk.objects.base.Place vkPlace = group.getPlace();
        if(vkPlace != null) {
            place.setCity(vkPlace.getCity());
            place.setCountry(vkPlace.getCountry());
            place.setAddress(vkPlace.getAddress());
            place.setLatitude(Double.valueOf(vkPlace.getLatitude()));
            place.setLongitude(Double.valueOf(vkPlace.getLongitude()));
        }
        event.setPlace(place);
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
