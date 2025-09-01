package com.hfing.pojo.dto;

public class StationDTO {
    private String stationName;
    private Double latitude;
    private Double longitude;

    public StationDTO(String stationName, Double latitude, Double longitude) {
        this.stationName = stationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters và setters
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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
}
