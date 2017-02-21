package org.ivt.agregator.rest.dto;

public class RemoveFromFavoriteRequest {

    private String token;
    private Long eventId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
