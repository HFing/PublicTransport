package com.hfing.services.impl;

import com.hfing.pojo.Station;
import com.hfing.repositories.StationRepository;
import com.hfing.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepo;

    @Override
    public Station saveStation(Station station) {
        return stationRepo.saveStation(station);
    }

    @Override
    public List<Station> getAllStations() {
        return stationRepo.getAllStations();
    }

    @Override
    public Station getStationById(int id) {
        return stationRepo.getStationById(id);
    }

    @Override
    public boolean deleteStation(int id) {
        return stationRepo.deleteStation(id);
    }

}
