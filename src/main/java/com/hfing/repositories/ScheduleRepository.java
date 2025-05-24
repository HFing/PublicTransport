package com.hfing.repositories;

import com.hfing.pojo.Schedule;
import java.util.List;
import java.sql.Date;

public interface ScheduleRepository {
    List<Schedule> getSchedules();
    Schedule getScheduleById(int id);
    Schedule addSchedule(Schedule schedule);
    Schedule updateSchedule(Schedule schedule);
    boolean deleteSchedule(int id);

    List<Schedule> getSchedulesFromDate(Date date);
}