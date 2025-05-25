package com.hfing.services.impl;

import com.hfing.pojo.Schedule;
import com.hfing.repositories.ScheduleRepository;
import com.hfing.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> getSchedules() {
        return scheduleRepository.getSchedules();
    }

    @Override
    public Schedule getScheduleById(int id) {
        return scheduleRepository.getScheduleById(id);
    }

    @Override
    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.addSchedule(schedule);
    }

    @Override
    public Schedule updateSchedule(Schedule schedule) {
        return scheduleRepository.updateSchedule(schedule);
    }

    @Override
    public boolean deleteSchedule(int id) {
        return scheduleRepository.deleteSchedule(id);
    }

    @Override
    public long countSchedules() {
        return scheduleRepository.countSchedules();
    }

    @Override
    public Map<String, Long> countSchedulesByRoute() {
        return scheduleRepository.countSchedulesByRoute();
    }

}