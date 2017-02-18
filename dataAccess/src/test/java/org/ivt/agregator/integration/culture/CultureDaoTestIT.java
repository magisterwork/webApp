package org.ivt.agregator.integration.culture;

import org.ivt.agregator.integration.httpUtils.HttpRequestService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertFalse;

public class CultureDaoTestIT {

    public static final int VOLOGDA_CITY_ID = 559;
    private CultureDao cultureDao;

    @BeforeClass
    public void setup() {
        cultureDao = new CultureDao(new HttpRequestService());
    }

    @Test
    public void shouldLoadEvents() {
        List events = cultureDao.findAll(VOLOGDA_CITY_ID, 50, 0);
        assertFalse(events.isEmpty());
    }
}