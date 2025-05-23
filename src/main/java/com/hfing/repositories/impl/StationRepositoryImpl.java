package com.hfing.repositories.impl;

import com.hfing.pojo.Station;
import com.hfing.repositories.StationRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class StationRepositoryImpl implements StationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getSession() {
        return factory.getObject().getCurrentSession();
    }

    @Override
    public Station saveStation(Station station) {
        if (station.getStationId() == null) {
            getSession().persist(station);  // insert
        } else {
            station = (Station) getSession().merge(station);  // update
        }
        return station;
    }

    @Override
    public List<Station> getAllStations() {
        return getSession().createQuery("FROM Station", Station.class).getResultList();
    }

    @Override
    public Station getStationById(int id) {
        return getSession().get(Station.class, id);
    }

    @Override
    public boolean deleteStation(int id) {
        Station station = getStationById(id);
        if (station != null) {
            getSession().remove(station);
            return true;
        }
        return false;
    }
}
