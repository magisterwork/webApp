package org.ivt.agregator.service;

import org.ivt.agregator.entity.Address;
import org.ivt.agregator.entity.Event;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class EventServiceMockImpl implements EventService {
    public List<Event> get() {
        ArrayList<Event> events = new ArrayList<Event>();
        for (int i = 0; i < 20; ++i) {
            events.add(getEvent(Long.valueOf(i), "Мероприятие" + i,
                    getAddress("Козлёнская", "33", "Вологда", "RU"),
                    new GregorianCalendar(2020 - i, 12, 12),
                    new GregorianCalendar()));
        }
        return events;
    }

    private Event getEvent(Long id, String name, Address address, GregorianCalendar endTime, GregorianCalendar beginTime) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setAddress(address);
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        return event;
    }

    private Address getAddress(String street, String house, String city, String countryIso) {
        Address address = new Address();
        address.setCity(city);
        address.setCountryIso(countryIso);
        address.setStreet(street);
        address.setHouse(house);
        return address;
    }
}
