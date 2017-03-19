package org.ivt.agregator.integration.google.places.dto;

public class GoogleGeometry {

    private GoogleLocation location;
    private GoogleViewport viewport;

    public GoogleLocation getLocation() {
        return location;
    }

    public void setLocation(GoogleLocation location) {
        this.location = location;
    }

    public GoogleViewport getViewport() {
        return viewport;
    }

    public void setViewport(GoogleViewport viewport) {
        this.viewport = viewport;
    }
}
