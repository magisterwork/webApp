package org.ivt.agregator.jobs;

import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.entity.Place;
import org.ivt.agregator.filter.CultureEventFilterBase;
import org.ivt.agregator.filter.EventAddingFilterBase;
import org.ivt.agregator.integration.culture.CultureDao;
import org.ivt.agregator.integration.culture.CultureMaterialConverter;
import org.ivt.agregator.integration.culture.entity.CultureMaterial;

import java.util.List;

public class CultureFiller implements EventFiller {

    public static final int VOLOGDA_CITY_ID = 559;
    public static final int COUNT_PER_PAGE = 50;

    private CultureDao cultureDao;
    private EventDao eventDao;
    private CultureEventFilterBase cultureEventFilter;
    private CultureMaterialConverter converter;
    private EventAddingFilterBase eventAddingFilter;

    private volatile int offset = 0;

    public CultureFiller(CultureDao cultureDao, EventDao eventDao, CultureEventFilterBase cultureEventFilter,
                         CultureMaterialConverter converter, EventAddingFilterBase eventAddingFilter) {
        this.cultureDao = cultureDao;
        this.eventDao = eventDao;
        this.cultureEventFilter = cultureEventFilter;
        this.converter = converter;
        this.eventAddingFilter = eventAddingFilter;
    }

    @Override
    public void loadEvents() {
        List<CultureMaterial> materials = cultureDao.findAll(VOLOGDA_CITY_ID, COUNT_PER_PAGE, offset);
        saveEvents(materials);
        modifyNextOffset(materials);
    }

    private void modifyNextOffset(List<CultureMaterial> materials) {
        offset += COUNT_PER_PAGE;
        if (materials.size() != COUNT_PER_PAGE) {
            offset = 0;
        }
    }

    private void saveEvents(List<CultureMaterial> materials) {
        for (CultureMaterial material : materials) {
            saveEvent(material);
        }
    }

    private void saveEvent(CultureMaterial material) {
        if (cultureEventFilter.shouldAdd(material)) {
            Event event = converter.convert(material);
            fillCity(event);
            if (eventAddingFilter.shouldAdd(event)) {
                eventDao.save(event);
            }
        }
    }

    private void fillCity(Event event) {
        Place place = event.getPlace();
        place.setCity("Вологда");
        place.setCountry("Россия");
    }
}
