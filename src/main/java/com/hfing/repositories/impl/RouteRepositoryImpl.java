package com.hfing.repositories.impl;

import com.hfing.pojo.Route;
import com.hfing.repositories.RouteRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public class RouteRepositoryImpl implements RouteRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getSession() {
        return this.factory.getObject().getCurrentSession();
    }

    @Override
    public List<Route> getRoutes() {
        return getSession().createQuery("FROM Route", Route.class).getResultList();
    }

    @Override
    public Route getRouteById(int id) {
        return getSession().get(Route.class, id);
    }

    @Override
    public Route addRoute(Route route) {
        getSession().persist(route);
        return route;
    }

    @Override
    public Route updateRoute(Route route) {
        getSession().merge(route);
        return route;
    }

    @Override
    public boolean deleteRoute(int id) {
        Route route = getRouteById(id);
        if (route != null) {
            getSession().remove(route);
            return true;
        }
        return false;
    }

    @Override
    public List<Route> searchRoutes(String from, String to, Date day) {
        String hql = """
        SELECT DISTINCT r
        FROM Route r
        JOIN FETCH r.schedules s
        JOIN FETCH r.routeStations rs
        JOIN FETCH rs.station st
        WHERE EXISTS (
            SELECT 1 FROM RouteStation rsFrom
            WHERE rsFrom.route = r AND rsFrom.station.stationName = :from
        )
        AND EXISTS (
            SELECT 1 FROM RouteStation rsTo
            WHERE rsTo.route = r AND rsTo.station.stationName = :to
              AND rsTo.stopOrder > (
                  SELECT rsFrom2.stopOrder
                  FROM RouteStation rsFrom2
                  WHERE rsFrom2.route = r AND rsFrom2.station.stationName = :from
              )
        )
        AND s.day = :day
    """;

        return getSession()
                .createQuery(hql, Route.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("day", day)
                .getResultList();
    }




}
