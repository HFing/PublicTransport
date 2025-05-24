package com.hfing.repositories.impl;

import com.hfing.pojo.Route;
import com.hfing.repositories.RouteRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

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


}
