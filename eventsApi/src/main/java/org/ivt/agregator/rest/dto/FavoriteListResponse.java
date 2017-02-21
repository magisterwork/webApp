package org.ivt.agregator.rest.dto;

import org.ivt.agregator.entity.Event;

import java.util.List;

public class FavoriteListResponse extends WithTokenResponse {

    List<Event> favorites;

    public List<Event> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Event> favorites) {
        this.favorites = favorites;
    }
}
