package org.ivt.agregator.entity;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String googleId;
    private String name;
    private String icon;
    private float googleRating;
    @ElementCollection(targetClass = PlaceCategory.class, fetch = EAGER)
    @Enumerated(EnumType.STRING)
    private Set<PlaceCategory> categories;
    private String imageUrl;

    private String country;

    private String city;
    private String address;
    private Double latitude;
    private Double longitude;

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCountry(String countryIso) {
        this.country = countryIso;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setGoogleRating(float googleRating) {
        this.googleRating = googleRating;
    }

    public float getGoogleRating() {
        return googleRating;
    }

    public void setCategories(Set<PlaceCategory> categories) {
        this.categories = categories;
    }

    public Set<PlaceCategory> getCategories() {
        return categories;
    }

    public String getIcon() {
        return icon;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
