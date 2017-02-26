package org.ivt.agregator.rest.dto;

public class IsFavoriteResponse extends WithTokenResponse {

    private Boolean isFavorite;

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
