package org.ivt.agregator.filter;

import org.ivt.agregator.integration.vk.VkGroup;

public class VkGroupFilterBase implements AddingFilter<VkGroup> {

    public static final int MIN_COUNT = 100;

    public boolean shouldAdd(VkGroup e) {
        return e.getStartDate() != null && e.getDescription() != null
                && e.getMembersCount() != null && e.getMembersCount() > MIN_COUNT;
    }
}
