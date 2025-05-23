package com.hfing.repositories.impl;

import com.hfing.pojo.RouteStation;
import com.hfing.repositories.RouteStationRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class RouteStationRepositoryImpl implements RouteStationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getSession() {
        return factory.getObject().getCurrentSession();
    }

    @Override
    public RouteStation save(RouteStation rs) {
        getSession().persist(rs);
        return rs;
    }

    @Override
    public List<RouteStation> getAll() {
        return getSession().createQuery("FROM RouteStation", RouteStation.class).getResultList();
    }

    @Override
    public RouteStation getById(int id) {
        return getSession().get(RouteStation.class, id);
    }

    @Override
    public RouteStation update(RouteStation rs) {
        getSession().merge(rs);
        return rs;
    }

    @Override
    public boolean delete(int id) {
        RouteStation rs = getById(id);
        if (rs != null) {
            getSession().remove(rs);
            return true;
        }
        return false;
    }
}
