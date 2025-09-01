package com.hfing.repositories;

import com.hfing.pojo.FavoriteRoute;
import com.hfing.pojo.Route;
import com.hfing.pojo.User;

import java.util.List;

public interface FavoriteRouteRepository {
    void addFavoriteRoute(FavoriteRoute favoriteRoute);
    boolean existsByUserAndRoute(int userId, int routeId);
    void removeFavoriteRoute(int userId, int routeId);
    List<Route> getFavoriteRoutesByUser(int userId);
}
