package org.ivt.agregator.integration;

public enum ExtSystem {
    VK("VK"),
    CULTURE("CULTURE");

    private String id;

    ExtSystem(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
