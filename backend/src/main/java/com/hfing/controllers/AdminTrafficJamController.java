package com.hfing.controllers;

import com.hfing.pojo.TrafficJamReport;
import com.hfing.repositories.TrafficJamReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminTrafficJamController {

    @Autowired
    private TrafficJamReportRepository reportRepository;

    @GetMapping("/admin/trafficjams")
    public String showTrafficJams(Model model) {
        List<TrafficJamReport> reports = reportRepository.getAllReports();
        model.addAttribute("reports", reports);
        return "admin/trafficjam/list";
    }

    @GetMapping("/admin/trafficjam/{id}")
    public String trafficJamDetail(@PathVariable("id") int id, Model model) {
        TrafficJamReport report = reportRepository.getReportById(id);
        System.out.println("Fetched report: " + report);
        if (report != null) {
            System.out.println("Image URL: " + report.getImageUrl());
            System.out.println("Report Time: " + report.getReportTime());
        }
        model.addAttribute("report", report);
        return "admin/trafficjam/detail";
    }
}