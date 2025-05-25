package com.hfing.services;

public interface TrafficJamReportService {
    void reportTrafficJam(int userId, String location, String description, String imageUrl);
}
