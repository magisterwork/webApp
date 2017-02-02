package org.ivt.agregator.jobs;

import org.ivt.agregator.converters.EventConverter;
import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.dao.VkGroupsDao;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.filter.EventAddingFilterBase;
import org.ivt.agregator.filter.VkGroupFilterBase;
import org.ivt.agregator.integration.vk.VkGroup;
import org.ivt.agregator.filter.AddingFilter;
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
    private VkGroupFilterBase vkGroupFilterBase;
    private EventDao eventDao;
    private EventAddingFilterBase eventAddingFilterBase;


    public VkFiller(EventConverter converter, VkGroupsDao groupsDao, VkSearchStringFactory stringFactory, VkGroupFilterBase vkGroupFilterBase, EventDao eventDao, EventAddingFilterBase eventAddingFilterBase) {
        this.converter = converter;
        this.groupsDao = groupsDao;
        this.stringFactory = stringFactory;
        this.vkGroupFilterBase = vkGroupFilterBase;
        this.eventDao = eventDao;
        this.eventAddingFilterBase = eventAddingFilterBase;
    }

    public void loadEvents() {
        logger.info("Events loading started");
        List<VkGroup> groups = groupsDao.get(stringFactory.getNext(), VOLOGDA_ID);
        logger.info(String.format("Got %s groups", groups.size()));
        for (VkGroup group : groups) {
            if (vkGroupFilterBase.shouldAdd(group)) {
                Event event = converter.convert(group);
                if (eventAddingFilterBase.shouldAdd(event)) {
                    try {
                        eventDao.save(event);
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Не удалось сохранить событие", e);
                    }
                }
            }
        }
    }
}
