package org.ivt.agregator.jobs;

import org.ivt.agregator.integration.vk.VkEventConverter;
import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.integration.vk.dao.VkGroupsDao;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.filter.EventAddingFilterBase;
import org.ivt.agregator.filter.VkGroupFilterBase;
import org.ivt.agregator.integration.vk.entity.VkGroup;
import org.ivt.agregator.utils.VkSearchStringFactory;

import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VkFiller implements EventFiller {

    public static final int VOLOGDA_ID = 41;
    private static Logger logger = Logger.getLogger(VkFiller.class.getName());
    private VkEventConverter converter;
    private VkGroupsDao groupsDao;
    private VkSearchStringFactory stringFactory;
    private VkGroupFilterBase vkGroupFilterBase;
    private EventDao eventDao;
    private EventAddingFilterBase eventAddingFilterBase;
    private Queue<String> groupIdsForPhotoLoadingQueue;


    public VkFiller(VkEventConverter converter, VkGroupsDao groupsDao, VkSearchStringFactory stringFactory,
                    VkGroupFilterBase vkGroupFilterBase, EventDao eventDao, EventAddingFilterBase eventAddingFilterBase,
                    Queue groupIdsForPhotoLoadingQueue) {
        this.converter = converter;
        this.groupsDao = groupsDao;
        this.stringFactory = stringFactory;
        this.vkGroupFilterBase = vkGroupFilterBase;
        this.eventDao = eventDao;
        this.eventAddingFilterBase = eventAddingFilterBase;
        this.groupIdsForPhotoLoadingQueue = groupIdsForPhotoLoadingQueue;
    }

    @Override
    public void loadEvents() {
        try {
            logger.info("Events loading started");
            load();
        } catch (Throwable e) {
            logger.severe("Error in loading events" + e);
        }
    }

    private void load() {
        List<VkGroup> groups = groupsDao.find(stringFactory.getNext(), VOLOGDA_ID);
        logger.info(String.format("Got %s groups", groups.size()));
        for (VkGroup group : groups) {
            checkAndSave(group);
        }
    }

    private void checkAndSave(VkGroup group) {
        if (vkGroupFilterBase.shouldAdd(group)) {
            Event event = converter.convert(group);
            if (eventAddingFilterBase.shouldAdd(event)) {
                try {
                    eventDao.save(event);
                    groupIdsForPhotoLoadingQueue.add(event.getExtId());
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Не удалось сохранить событие", e);
                }
            }
        }
    }
}
