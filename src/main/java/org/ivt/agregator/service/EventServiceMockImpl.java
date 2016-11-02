package org.ivt.agregator.service;

import org.ivt.agregator.entity.Address;
import org.ivt.agregator.entity.Event;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class EventServiceMockImpl implements EventService {
    public List<Event> get() {
        ArrayList<Event> events = new ArrayList<Event>();

        events.add(getEvent("Мероприятие1",
                getAddress("Козлёнская", "33", "Вологда", "RU"),
                new GregorianCalendar(2017, 12, 12),
                new GregorianCalendar()));

        events.add(getEvent("Мероприятие2",
                getAddress("Фрязиновская", "27", "Вологда", "RU"),
                new GregorianCalendar(2016, 11, 12),
                new GregorianCalendar(2018, 11, 11)));

        return events;
    }

    private Event getEvent(String name, Address address, GregorianCalendar endTime, GregorianCalendar beginTime) {
        Event event = new Event();
        event.setId(1l);
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
