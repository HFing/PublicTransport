package com.hfing.services;

import com.hfing.pojo.Route;

import java.util.List;

public interface  FavoriteRouteService {
    void addFavorite(Integer userId, Integer routeId);
    void removeFavorite(Integer userId, Integer routeId);
    List<Route> getFavoriteRoutesByUser(int userId);
}
