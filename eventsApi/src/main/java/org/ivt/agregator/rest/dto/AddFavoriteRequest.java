package org.ivt.agregator.rest.dto;

public class AddFavoriteRequest extends WithTokenRequest{

    private Long eventId;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
