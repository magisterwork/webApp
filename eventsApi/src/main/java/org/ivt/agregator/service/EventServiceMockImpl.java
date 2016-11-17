package org.ivt.agregator.service;

import org.ivt.agregator.entity.Address;
import org.ivt.agregator.entity.Event;

import java.util.*;

public class EventServiceMockImpl implements EventService {

    private static final String[] URLES = {
            "http://event.utimes.ru/images/fe5190e8e2715935c043427d68d40ace.jpg",
            "http://astroway.ucoz.ru/sovmestimost3.jpg",
            "http://kitchentalks.ru/wp-content/uploads/2016/02/Kitchentalks_business_meeting.jpg",
            "https://blog.anketolog.ru/wp-content/uploads/2015/09/b9e80bf0a55cec46b471564517.jpg",
            "http://event2.utimes.ru/upload/users/0/525/all/2d/03-07-2013-1.jpg"
    };

    private static final String[] DESCRIPTIONS = {
            "Обязательно будет интересно и весело.",
            "Мы делаем только крутые мероприятия",
            "Приходите и не пожалеете",
            "На входе бесплатные напитки",
            "Дети до 15 лет проходят беслпатно"
    };

    public List<Event> get(int offset, int count) {
        ArrayList<Event> events = new ArrayList<Event>();
        for (int i = 0; i < 20; ++i) {
            events.add(getEvent(Long.valueOf(i), "Мероприятие" + i,
                    getAddress("Козлёнская", "33", "Вологда", "RU"),
                    new GregorianCalendar().getTime(),
                    new Date()));
        }
        return events;
    }

    private Event getEvent(Long id, String name, Address address, Date endTime, Date beginTime) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setDescription("Описание №" + id + " адрес: " + address + "начнётся в " + beginTime + DESCRIPTIONS[getRandInt()]);
        event.setImageUrl(URLES[getRandInt()]);
        event.setAddress(address);
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        return event;
    }

        private int getRandInt() {
            return new Random().nextInt(5);
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
