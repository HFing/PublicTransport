package com.hfing.services;

import com.hfing.pojo.Station;

import java.util.List;

public interface StationService {
    Station saveStation(Station station);
    List<Station> getAllStations();
    Station getStationById(int id);
    boolean deleteStation(int id);

}