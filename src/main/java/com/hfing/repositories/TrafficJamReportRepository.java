package com.hfing.repositories;

import com.hfing.pojo.TrafficJamReport;

public interface TrafficJamReportRepository {
    void saveReport(TrafficJamReport report);
}