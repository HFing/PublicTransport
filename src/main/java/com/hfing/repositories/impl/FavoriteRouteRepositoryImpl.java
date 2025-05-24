package com.hfing.repositories.impl;

import com.hfing.pojo.FavoriteRoute;

import com.hfing.pojo.Route;
import com.hfing.repositories.FavoriteRouteRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class FavoriteRouteRepositoryImpl implements FavoriteRouteRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return this.factory.getObject().getCurrentSession();
    }

    @Override
    public void addFavoriteRoute(FavoriteRoute favoriteRoute) {
        getCurrentSession().save(favoriteRoute);
    }

    @Override
    public boolean existsByUserAndRoute(int userId, int routeId) {
        String hql = "SELECT COUNT(f) FROM FavoriteRoute f WHERE f.user.userId = :userId AND f.route.routeId = :routeId";
        Query<Long> query = getCurrentSession().createQuery(hql, Long.class);
        query.setParameter("userId", userId);
        query.setParameter("routeId", routeId);
        Long count = query.uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public void removeFavoriteRoute(int userId, int routeId) {
        String hql = "DELETE FROM FavoriteRoute f WHERE f.user.userId = :userId AND f.route.routeId = :routeId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("routeId", routeId);
        query.executeUpdate();
    }

    @Override
    public List<Route> getFavoriteRoutesByUser(int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        String hql = "SELECT f.route FROM FavoriteRoute f WHERE f.user.userId = :userId";
        return s.createQuery(hql, Route.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
