package com.hfing.repositories.impl;

import com.hfing.pojo.Notification;
import com.hfing.pojo.Schedule;
import com.hfing.pojo.SystemNotification;
import com.hfing.repositories.NotificationRepository;
import com.hfing.repositories.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import java.sql.Date;

import java.util.List;

@Repository
@Transactional
public class ScheduleRepositoryImpl implements ScheduleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private NotificationRepository notificationRepository;

    private Session getSession() {
        return this.factory.getObject().getCurrentSession();
    }

    @Override
    public List<Schedule> getSchedules() {
        return getSession().createQuery("FROM Schedule", Schedule.class).getResultList();
    }

    @Override
    public Schedule getScheduleById(int id) {
        String hql = "SELECT s FROM Schedule s " +
                "JOIN FETCH s.route r " +
                "JOIN FETCH r.routeStations " +
                "WHERE s.scheduleId = :id";
        return getSession().createQuery(hql, Schedule.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public Schedule addSchedule(Schedule schedule) {
        getSession().persist(schedule);

        List<Notification> notifies = notificationRepository.getUsersByRoute(schedule.getRoute().getRouteId());
        for (Notification notify : notifies) {
            if (Boolean.TRUE.equals(notify.getNotifyOnChanges())) {
                SystemNotification sys = new SystemNotification();
                sys.setUser(notify.getUser());
                sys.setContent("Tuyến " + schedule.getRoute().getRouteName() + " có lịch trình mới.");
                getSession().persist(sys);
            }
        }
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

    @Override
    public List<Schedule> getSchedulesFromDate(Date date) {
        String hql = "FROM Schedule s WHERE s.day >= :date ORDER BY s.day, s.startTime";
        return getSession().createQuery(hql, Schedule.class)
                .setParameter("date", date)
                .getResultList();
    }
}