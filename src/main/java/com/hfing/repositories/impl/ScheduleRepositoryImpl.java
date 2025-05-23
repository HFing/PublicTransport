package com.hfing.repositories.impl;

import com.hfing.pojo.Schedule;
import com.hfing.repositories.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ScheduleRepositoryImpl implements ScheduleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getSession() {
        return this.factory.getObject().getCurrentSession();
    }

    @Override
    public List<Schedule> getSchedules() {
        return getSession().createQuery("FROM Schedule", Schedule.class).getResultList();
    }

    @Override
    public Schedule getScheduleById(int id) {
        return getSession().get(Schedule.class, id);
    }

    @Override
    public Schedule addSchedule(Schedule schedule) {
        getSession().persist(schedule);
        return schedule;
    }

    @Override
    public Schedule updateSchedule(Schedule schedule) {
        getSession().merge(schedule);
        return schedule;
    }

    @Override
    public boolean deleteSchedule(int id) {
        Schedule schedule = getScheduleById(id);
        if (schedule != null) {
            getSession().remove(schedule);
            return true;
        }
        return false;
    }
}