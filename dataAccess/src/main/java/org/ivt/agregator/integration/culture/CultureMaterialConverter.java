package org.ivt.agregator.integration.culture;

import org.apache.commons.lang3.Validate;
import org.ivt.agregator.converters.EventConverter;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.entity.Place;
import org.ivt.agregator.integration.ExtSystem;
import org.ivt.agregator.integration.culture.entity.CultureMaterial;
import org.ivt.agregator.integration.culture.entity.Thumbnail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CultureMaterialConverter implements EventConverter<CultureMaterial> {

    public static final Pattern LATITUDE_PATTERN = Pattern.compile("([+-]?\\d+\\.?\\d+)\\s*,\\s*([+-]?\\d+\\.?\\d+)");

    @Override
    public Event convert(CultureMaterial material) {
        Validate.notNull(material);
        Event event = new Event();
        event.setBeginTime(material.getStartDate());
        event.setDescription(material.getDescription() + "\n" + material.getPrice());
        event.setName(material.getTitle());
        event.setExtSystem(ExtSystem.CULTURE);
        event.setExtId(String.valueOf(material.getId()));
        event.setEndTime(material.getEnd_date());
        fillPlace(material, event);
        fillImages(material, event);

        return event;
    }

    private void fillImages(CultureMaterial material, Event event) {
        Thumbnail thumbnail = material.getThumbnail();
        String url = String.format("http://%s.culture.ru/c/%s.jpg", thumbnail.getHost(), thumbnail.getFile_id());
        event.setImageUrl(url);
        event.setPreviewUrl(url);
    }

    private void fillPlace(CultureMaterial material, Event event) {
        Place place = new Place();
        place.setAddress(material.getAddress());
        fillCoordinates(material, place);
        event.setPlace(place);
    }

    private void fillCoordinates(CultureMaterial material, Place place) {
        String ll = material.getLl();
        Matcher matcher = LATITUDE_PATTERN.matcher(ll);
        String latitude = matcher.group();
        String longitude = matcher.group();
        place.setLatitude(Double.valueOf(latitude));
        place.setLongitude(Double.valueOf(longitude));
    }
}
