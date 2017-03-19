package org.ivt.agregator.integration.google.places.dto;

import java.util.List;

public class GooglePhoto {

    private String photo_reference;
    private List<String> html_attributions;

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<String> html_attributions) {
        this.html_attributions = html_attributions;
    }
}
