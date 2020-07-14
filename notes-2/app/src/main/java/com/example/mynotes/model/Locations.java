package com.example.mynotes.model;

public class Locations {
    float lat,lon;
    String added_on;
    int id;

    public Locations(float lat, float lon, String added_on, int id) {
        this.lat = lat;
        this.lon = lon;
        this.added_on = added_on;
        this.id = id;
    }
}
