package org.ivt.agregator.integration.google.places.dto;

public class GoogleViewport {

    private GoogleLocation northeast;
    private GoogleLocation southwest;

    public GoogleLocation getNortheast() {
        return northeast;
    }

    public void setNortheast(GoogleLocation northeast) {
        this.northeast = northeast;
    }

    public GoogleLocation getSouthwest() {
        return southwest;
    }

    public void setSouthwest(GoogleLocation southwest) {
        this.southwest = southwest;
    }
}
