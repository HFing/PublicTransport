package com.hfing.repositories;

import com.hfing.pojo.Schedule;
import java.util.List;
import java.sql.Date;
import java.util.Map;

public interface ScheduleRepository {
    List<Schedule> getSchedules();
    Schedule getScheduleById(int id);
    Schedule addSchedule(Schedule schedule);
    Schedule updateSchedule(Schedule schedule);
    boolean deleteSchedule(int id);
    long countSchedules();
    List<Schedule> getSchedulesFromDate(Date date);
    Map<String, Long> countSchedulesByRoute();

}