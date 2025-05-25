package com.hfing.services.impl;

import com.hfing.pojo.TrafficJamReport;
import com.hfing.pojo.User;
import com.hfing.repositories.TrafficJamReportRepository;
import com.hfing.repositories.UserRepository;
import com.hfing.services.TrafficJamReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TrafficJamReportServiceImpl implements TrafficJamReportService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TrafficJamReportRepository reportRepo;

    @Override
    public void reportTrafficJam(int userId, String location, String description, String imageUrl) {
        User user = userRepo.getUserById(userId);
        if (user == null) throw new RuntimeException("Không tìm thấy người dùng");

        TrafficJamReport report = new TrafficJamReport();
        report.setUser(user);
        report.setLocation(location);
        report.setDescription(description);
        report.setImageUrl(imageUrl);
        report.setReportTime(new Timestamp(System.currentTimeMillis()));

        reportRepo.saveReport(report);
    }

    @Override
    public TrafficJamReport getReportById(int id) {
        return reportRepo.getReportById(id);
    }

}