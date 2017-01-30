package org.ivt.agregator.jobs;

import com.vk.api.sdk.objects.groups.GroupFull;
import org.ivt.agregator.converters.EventConverter;
import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.dao.VkGroupsDao;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.integration.vk.VkGroup;
import org.ivt.agregator.utils.EventAddingFilter;
import org.ivt.agregator.utils.VkSearchStringFactory;

import java.util.List;

public class VkFiller {

    public static final int VOLOGDA_ID = 41;
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
        List<Event> sourceEvents = converter.convert(groups);
        List<Event> filteredEvents = this.filter.filter(sourceEvents);
        for (Event event : filteredEvents) {
            eventDao.save(event);
        }
    }
}
