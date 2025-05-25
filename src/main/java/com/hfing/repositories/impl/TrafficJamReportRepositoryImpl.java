package com.hfing.repositories.impl;

import com.hfing.pojo.TrafficJamReport;
import com.hfing.repositories.TrafficJamReportRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class TrafficJamReportRepositoryImpl implements TrafficJamReportRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getSession() {
        return factory.getObject().getCurrentSession();
    }

    @Override
    public void saveReport(TrafficJamReport report) {
        getSession().save(report);
    }

    @Override
    public TrafficJamReport getReportById(int id) {
        TrafficJamReport report = getSession().get(TrafficJamReport.class, id);
        if (report != null && report.getUser() != null) {
            // Use the correct getter for User's ID
            report.getUser().getUserId();
        }
        return report;
    }

    @Override
    public List<TrafficJamReport> getAllReports() {
        String hql = "FROM TrafficJamReport";
        List<TrafficJamReport> reports = getSession().createQuery(hql, TrafficJamReport.class).getResultList();
        System.out.println("Fetched reports: " + reports.size());
        return reports;
    }
}
