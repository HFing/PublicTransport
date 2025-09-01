package com.hfing.services;

import com.hfing.pojo.Schedule;
import java.util.List;
import java.util.Map;

public interface ScheduleService {
    List<Schedule> getSchedules();
    Schedule getScheduleById(int id);
    Schedule addSchedule(Schedule schedule);
    Schedule updateSchedule(Schedule schedule);
    boolean deleteSchedule(int id);
    long countSchedules();
    Map<String, Long> countSchedulesByRoute();
}