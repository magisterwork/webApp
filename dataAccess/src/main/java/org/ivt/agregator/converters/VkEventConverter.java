package org.ivt.agregator.converters;

import org.ivt.agregator.entity.Place;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.integration.ExtSystem;
import org.ivt.agregator.dao.vk.VkAddressDao;
import org.ivt.agregator.integration.vk.entity.VkGroup;

import java.util.Date;

public class VkEventConverter {

    private VkAddressDao vkAddressDao;

    public static final long MILLIS_IN_SECOND = 1000l;

    public VkEventConverter(VkAddressDao vkAddressDao) {
        this.vkAddressDao = vkAddressDao;
    }

    public Event convert(VkGroup group) {
        Event event = new Event();
        event.setExtId(group.getId());
        event.setName(group.getName());
        event.setDescription(group.getDescription());
        event.setExtSystem(ExtSystem.VK);
        addStartDate(group, event);
        addFinishDate(group, event);
        addPlace(group, event);
        event.setPreviewUrl(group.getPhoto200());

        return event;
    }

    private void addPlace(VkGroup group, Event event) {
        Place place = new Place();
        com.vk.api.sdk.objects.base.Place vkPlace = group.getPlace();
        if(vkPlace != null) {
            String cityId = vkPlace.getCity();
            place.setCity(vkAddressDao.getCity(cityId));
            String countryId = vkPlace.getCountry();
            place.setCountry(vkAddressDao.getCountry(countryId));
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
