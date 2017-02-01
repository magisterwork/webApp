package org.ivt.agregator.jobs;

import org.ivt.agregator.converters.EventConverter;
import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.dao.VkGroupsDao;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.integration.vk.VkGroup;
import org.ivt.agregator.utils.EventAddingFilter;
import org.ivt.agregator.utils.VkSearchStringFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VkFiller {

    public static final int VOLOGDA_ID = 41;
    private static Logger logger = Logger.getLogger(VkFiller.class.getName());
    private EventConverter converter;
    private VkGroupsDao groupsDao;
    private VkSearchStringFactory stringFactory;
    private EventAddingFilter filter;
    private EventDao eventDao;


    public VkFiller(EventConverter converter, VkGroupsDao groupsDao, VkSearchStringFactory stringFactory, EventAddingFilter filter, EventDao eventDao) {
        this.converter = converter;
        this.groupsDao = groupsDao;
        this.stringFactory = stringFactory;
        this.filter = filter;
        this.eventDao = eventDao;
    }

    public void loadEvents() {
        List<VkGroup> groups = groupsDao.get(stringFactory.getNext(), VOLOGDA_ID);
        for (VkGroup group : groups) {
            Event event = converter.convert(group);
            if (filter.shouldAdd(event)) {
                try {
                    eventDao.save(event);
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Не удалось сохранить событие", e);
                }
            }
        }
    }
}
