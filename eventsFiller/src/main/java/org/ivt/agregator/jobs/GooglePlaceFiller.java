package org.ivt.agregator.jobs;

import org.ivt.agregator.dao.PlaceDao;
import org.ivt.agregator.integration.google.places.GooglePlaceConverter;
import org.ivt.agregator.integration.google.places.GooglePlacesDao;
import org.ivt.agregator.integration.google.places.dto.GooglePlace;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class GooglePlaceFiller {

    private final static Logger LOGGER = Logger.getLogger(GooglePlaceFiller.class.getName());
    private final static String LATITUDE = "59.217355";
    private final static String LONGITUDE = "39.885763";
    private final long radius = 10000;
    private final List<String> categories;

    private PlaceDao placeDao;
    private GooglePlacesDao googlePlacesDao;
    private GooglePlaceConverter placeConverter;

    public GooglePlaceFiller(PlaceDao placeDao, GooglePlacesDao googlePlacesDao, GooglePlaceConverter placeConverter) {
        this.placeDao = placeDao;
        this.googlePlacesDao = googlePlacesDao;
        this.placeConverter = placeConverter;
        categories = Arrays.asList("cafe", "bar", "movie_theater", "casino", "museum", "night_club", "restaurant", "zoo");
    }

    public void loadAll() {
        LOGGER.info("Загрузка заведений из гугл");
        GooglePlacesDao.GooglePlaces googlePlaces = googlePlacesDao.getNears(LATITUDE, LONGITUDE, radius, categories);
        for (GooglePlace googlePlace : googlePlaces) {
            placeDao.save(placeConverter.convert(googlePlace));
        }
    }
}
