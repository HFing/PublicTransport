package com.hfing.repositories;

import com.hfing.pojo.TrafficJamReport;
import java.util.List;

public interface TrafficJamReportRepository {
    void saveReport(TrafficJamReport report);
    long countReports();
    List<TrafficJamReport> getAllReports();
    TrafficJamReport getReportById(int id);
}