package org.ivt.agregator.jobs;

import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.dao.vk.VkFullImageDao;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.integration.ExtSystem;

import java.util.Queue;

public class VkImageFiller {

    private VkFullImageDao imageDao;
    private Queue<String> groupIdsForPhotoLoadingQueue;
    private EventDao eventDao;

    public VkImageFiller(VkFullImageDao imageDao, Queue<String> photoOwnersIdQueue, EventDao eventDao) {
        this.imageDao = imageDao;
        groupIdsForPhotoLoadingQueue = photoOwnersIdQueue;
        this.eventDao = eventDao;
    }

    public void loadNext() {
        if (!groupIdsForPhotoLoadingQueue.isEmpty()) {
            String groupId = groupIdsForPhotoLoadingQueue.remove();
            String fullImageUrl = imageDao.getFullImageUrl(groupId);
            Event event = eventDao.getByExtId(groupId, ExtSystem.VK);
            event.setImageUrl(fullImageUrl);
            eventDao.save(event);
        }
    }
}
