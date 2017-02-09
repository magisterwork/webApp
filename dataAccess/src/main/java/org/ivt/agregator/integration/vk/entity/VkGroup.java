package org.ivt.agregator.integration.vk.entity;

import com.vk.api.sdk.objects.base.Place;
import com.vk.api.sdk.objects.groups.GroupFull;

public class VkGroup extends GroupFull {

    private Place place;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
