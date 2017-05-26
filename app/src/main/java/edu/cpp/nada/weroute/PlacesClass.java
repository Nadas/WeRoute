package edu.cpp.nada.weroute;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nada on 4/26/17.
 */

public class PlacesClass {

    private LatLng latLang;
    private String name;
    private Boolean favorite;
    private String city;

    public PlacesClass() {
    }

    public PlacesClass(LatLng latLang, String name, Boolean favorite, String city) {
        this.latLang = latLang;
        this.name = name;
        this.favorite = favorite;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLang() {
        return latLang;
    }

    public void setLatLang(LatLng latLang) {
        this.latLang = latLang;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }
}
