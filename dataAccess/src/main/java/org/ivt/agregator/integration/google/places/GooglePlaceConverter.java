package org.ivt.agregator.integration.google.places;

import org.ivt.agregator.entity.Place;
import org.ivt.agregator.entity.PlaceCategory;
import org.ivt.agregator.integration.google.places.dto.GoogleLocation;
import org.ivt.agregator.integration.google.places.dto.GooglePhoto;
import org.ivt.agregator.integration.google.places.dto.GooglePlace;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class GooglePlaceConverter {

    private final static Logger LOGGER = Logger.getLogger(GooglePlaceConverter.class.getName());
    private final static Set<String> ignorableCategories = new HashSet<>();
    public static final int PHOTO_MAX_WIDTH = 807;

    {
        ignorableCategories.add("establishment");
        ignorableCategories.add("point_of_interest");
    }

    public GooglePlaceConverter(GooglePhotoService photoService) {
        this.photoService = photoService;
    }

    private GooglePhotoService photoService;

    public Place convert(GooglePlace gPlace) {
        Place place = new Place();
        place.setGoogleId(gPlace.getPlace_id());
        if (gPlace.getVicinity() != null) {
            place.setAddress(gPlace.getVicinity());
        } else {
            place.setAddress(gPlace.getFormatted_address());
        }
        if (gPlace.getGeometry() != null && gPlace.getGeometry().getLocation() != null) {
            GoogleLocation location = gPlace.getGeometry().getLocation();
            place.setLatitude(location.getLat());
            place.setLongitude(location.getLng());
        }
        place.setName(gPlace.getName());
        place.setIcon(gPlace.getIcon());
        place.setGoogleRating(gPlace.getRating());

        HashSet<PlaceCategory> categories = convertCategories(gPlace);
        place.setCategories(categories);

        List<GooglePhoto> photos = gPlace.getPhotos();
        if (photos != null && !photos.isEmpty()) {
            place.setImageUrl(photoService.getUrl(photos.get(0).getPhoto_reference(), PHOTO_MAX_WIDTH));
        }
        return place;
    }

    private HashSet<PlaceCategory> convertCategories(GooglePlace gPlace) {
        HashSet<PlaceCategory> categories = new HashSet<>();
        gPlace.getTypes().stream().filter(type -> !ignorableCategories.contains(type)).forEach(type -> {
            try {
                categories.add(PlaceCategory.valueOf(type.toUpperCase()));
            } catch (IllegalArgumentException e) {
                LOGGER.warning("Несуществующая в системе категория заведения: " + type);
            }
        });
        return categories;
    }
}
