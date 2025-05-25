package com.hfing.repositories.impl;

import com.hfing.pojo.TrafficJamReport;
import com.hfing.repositories.TrafficJamReportRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

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
}
