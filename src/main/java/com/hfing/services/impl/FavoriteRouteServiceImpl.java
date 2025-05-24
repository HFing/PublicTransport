package com.hfing.services.impl;

import com.hfing.pojo.FavoriteRoute;
import com.hfing.pojo.Route;
import com.hfing.pojo.User;
import com.hfing.repositories.FavoriteRouteRepository;
import com.hfing.repositories.RouteRepository;
import com.hfing.repositories.UserRepository;
import com.hfing.services.FavoriteRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteRouteServiceImpl implements FavoriteRouteService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private FavoriteRouteRepository favoriteRouteRepo;

    @Override
    public void addFavorite(Integer userId, Integer routeId) {
        // Kiểm tra tồn tại trước khi thêm
        if (favoriteRouteRepo.existsByUserAndRoute(userId, routeId)) {
            throw new RuntimeException("Tuyến đường này đã có trong danh sách yêu thích.");
        }

        User user = userRepo.getUserById(userId);
        if (user == null) throw new RuntimeException("Không tìm thấy người dùng.");

        Route route = routeRepo.getRouteById(routeId);
        if (route == null) throw new RuntimeException("Không tìm thấy tuyến đường.");

        FavoriteRoute fav = new FavoriteRoute();
        fav.setUser(user);
        fav.setRoute(route);

        favoriteRouteRepo.addFavoriteRoute(fav);
    }

    @Override
    public void removeFavorite(Integer userId, Integer routeId) {

        if (!favoriteRouteRepo.existsByUserAndRoute(userId, routeId)) {
            throw new RuntimeException("Tuyến đường này không có trong danh sách yêu thích.");
        }

        favoriteRouteRepo.removeFavoriteRoute(userId, routeId);
    }

    @Override
    public List<Route> getFavoriteRoutesByUser(int userId) {
        return favoriteRouteRepo.getFavoriteRoutesByUser(userId);
    }


}
