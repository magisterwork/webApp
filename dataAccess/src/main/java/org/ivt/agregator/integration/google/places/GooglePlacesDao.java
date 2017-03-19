package org.ivt.agregator.integration.google.places;

import com.google.gson.Gson;
import org.ivt.agregator.dao.ParameterDao;
import org.ivt.agregator.integration.google.places.dto.GooglePlace;
import org.ivt.agregator.integration.google.places.dto.GooglePlaceResponse;
import org.ivt.agregator.integration.google.places.exception.GooglePlacesDaoException;
import org.ivt.agregator.integration.httpUtils.HttpRequestService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.ivt.agregator.entity.Parameter.GKEY;

public class GooglePlacesDao {

    private final static Logger LOGGER = Logger.getLogger(GooglePlacesDao.class.getName());
    private final static Gson GSON = new Gson();
    private final static String PLACES_URL = "https://maps.googleapis.com/maps/api/place/";
    private final static String NEAR_BY_METHOD = "nearbysearch";
    private final static String JSON_TYPE = "json";
    private final static String NEAR_BY_PARAMS_TEMPLATE = "key=%s&location=%s,%s&radius=%s&type=%s&language=ru";
    private final static String TYPE_DELIMITER = "|";
    private final static String WITH_PAGETOKEN_TEMPLATE = "pagetoken=%s&key=%s&language=ru";

    private HttpRequestService httpRequestService;
    private ParameterDao parameterDao;

    public GooglePlacesDao(HttpRequestService httpRequestService, ParameterDao parameterDao) {
        this.httpRequestService = httpRequestService;
        this.parameterDao = parameterDao;
    }

    public GooglePlaces getNears(String latitude, String longitude, long radius, List<String> categories) {
        URL url = getNearUrl(latitude, longitude, radius, join(TYPE_DELIMITER, categories));
        LOGGER.info("Получаем места от гугла с параметрами " + latitude + " " + longitude + " " + radius + " " + categories);
        String json = httpRequestService.doGetRequest(url);
        GooglePlaceResponse response = GSON.fromJson(json, GooglePlaceResponse.class);
        return new GooglePlaces(response);
    }

    private URL getNearUrl(String latitude, String longitude, long radius, String categories) {
        String parameters = format(NEAR_BY_PARAMS_TEMPLATE, parameterDao.get(GKEY).getValue(), latitude, longitude, radius, categories);
        StringBuilder urlBuilder = new StringBuilder(PLACES_URL).append(NEAR_BY_METHOD).append("/").append(JSON_TYPE)
                .append("?").append(parameters);
        try {
            return new URL(urlBuilder.toString());
        } catch (MalformedURLException e) {
            throw new GooglePlacesDaoException("Не удалось сформировать URL.", e);
        }
    }

    private GooglePlaces getNearByToken(String searchToken) {
        URL url = getNearTokenUrl(searchToken);
        LOGGER.info("Получаем места от гугла по токену");
        String json = httpRequestService.doGetRequest(url);
        GooglePlaceResponse response = GSON.fromJson(json, GooglePlaceResponse.class);
        return new GooglePlaces(response);
    }

    private URL getNearTokenUrl(String searchToken) {
        StringBuilder urlBuilder = new StringBuilder(PLACES_URL).append(NEAR_BY_METHOD).append("/").append(JSON_TYPE)
                .append("?").append(format(WITH_PAGETOKEN_TEMPLATE, searchToken, parameterDao.get(GKEY).getValue()));
        try {
            return new URL(urlBuilder.toString());
        } catch (MalformedURLException e) {
            throw new GooglePlacesDaoException("Не удалось сформировать URL.", e);
        }
    }

    public class GooglePlaces implements Iterator<GooglePlace>, Iterable<GooglePlace> {

        private String searchToken;
        private Iterator<GooglePlace> innerIterator;

        protected GooglePlaces(GooglePlaceResponse response) {
            searchToken = response.getNext_page_token();
            innerIterator = response.getResults().listIterator();
        }

        @Override
        public boolean hasNext() {
            return innerIterator.hasNext() || isNotEmpty(searchToken);
        }

        @Override
        public GooglePlace next() {
            if (innerIterator.hasNext()) {
                return innerIterator.next();
            } else if (isNotEmpty(searchToken)) {
                GooglePlaces newPlaces = getNearByToken(searchToken);
                innerIterator = newPlaces.innerIterator;
                searchToken = newPlaces.searchToken;
                return next();
            }
            throw new NoSuchElementException();
        }

        @Override
        public Iterator<GooglePlace> iterator() {
            return this;
        }
    }
}
