package org.ivt.agregator.integration.google.places;

import org.ivt.agregator.dao.ParameterDao;
import org.ivt.agregator.entity.Parameter;
import org.ivt.agregator.integration.httpUtils.HttpRequestService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import static java.lang.String.format;

public class GooglePhotoService {

    private static final Logger LOGGER = Logger.getLogger(GooglePhotoService.class.getName());

    private ParameterDao parameterDao;
    private HttpRequestService requestService;
    public static final String URL_TEMPLATE = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=%s&photoreference=%s&key=%s";

    public GooglePhotoService(ParameterDao parameterDao, HttpRequestService requestService) {
        this.parameterDao = parameterDao;
        this.requestService = requestService;
    }

    public String getUrl(String reference, int maxWidth) {
        try {
            URL url = new URL(format(URL_TEMPLATE, maxWidth, reference, parameterDao.get(Parameter.GKEY).getValue()));
            return requestService.getRedirectUrl(url);
        } catch (MalformedURLException e) {
            LOGGER.warning(e.toString());
        }
        return "";
    }
}
