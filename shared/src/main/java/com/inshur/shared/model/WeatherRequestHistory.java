package com.inshur.shared.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class WeatherRequestHistory {

    @JsonProperty
    private Date requestDateTime;
    @JsonProperty
    private double lat;
    @JsonProperty
    private double lon;

    @JsonProperty
    private String status;

    public WeatherRequestHistory() {}

    public WeatherRequestHistory(Date requestDateTime, double lat, double lon, String status) {
        this.requestDateTime = requestDateTime;
        this.lat = lat;
        this.lon = lon;
        this.status = status;
    }

    public Date getRequestDateTime() {
        return requestDateTime;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getStatus() {
        return status;
    }

}
