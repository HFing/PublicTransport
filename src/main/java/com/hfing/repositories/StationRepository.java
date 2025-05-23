package com.hfing.repositories;

import com.hfing.pojo.Station;
import java.util.List;

public interface StationRepository {
    Station saveStation(Station station);
    List<Station> getAllStations();
    Station getStationById(int id);
    boolean deleteStation(int id);
}