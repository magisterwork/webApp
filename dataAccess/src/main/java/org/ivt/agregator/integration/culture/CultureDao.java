package org.ivt.agregator.integration.culture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ivt.agregator.dao.ExternalEventDao;
import org.ivt.agregator.integration.culture.entity.CultureMaterial;
import org.ivt.agregator.integration.culture.entity.CultureResponse;
import org.ivt.agregator.integration.httpUtils.HttpRequestService;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class CultureDao implements ExternalEventDao<CultureMaterial> {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
    protected static final Gson GSON = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
    protected static final Logger LOGGER = Logger.getLogger(CultureDao.class.getName());
    private static final  SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);
    private static final String CULTURE_URL = "http://www.culture.ru/api/v1/materials";

    public static final String PARAMS = "?type=event&city=%s&page=%s&page_size=%s&start_date_from=%s";
    private HttpRequestService httpRequestService;

    public CultureDao(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    @Override
    public List<CultureMaterial> findAll(int cityId, int count, long offset) {
        try {
            LOGGER.info(String.format("Получение событий из culture.ru cityId = %s count = %s offset = %s", cityId, count, offset));
            long page = offset / count;
            URL url = new URL(String.format(CULTURE_URL + PARAMS, cityId, page, count, SIMPLE_DATE_FORMAT.format(new Date())));
            String response = httpRequestService.doGetRequest(url);
            CultureResponse cultureResponse = GSON.fromJson(response, CultureResponse.class);
            return cultureResponse.getData();
        } catch (Exception e) {
            throw new CultureDaoException(e);
        }
    }

    @Override
    public List<CultureMaterial> find(String text, int cityId) {
        throw new UnsupportedOperationException();
    }
}
