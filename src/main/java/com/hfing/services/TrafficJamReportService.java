package com.hfing.services;
import com.hfing.pojo.TrafficJamReport;

public interface TrafficJamReportService {
    void reportTrafficJam(int userId, String location, String description, String imageUrl);

    TrafficJamReport getReportById(int id);
}
