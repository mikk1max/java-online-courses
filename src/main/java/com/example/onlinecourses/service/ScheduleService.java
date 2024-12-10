package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Schedule;
import com.example.onlinecourses.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        Schedule existingSchedule = scheduleRepository.findById(id);
        if (existingSchedule == null) {
            throw new RuntimeException("Schedule not found");
        }

        if (updatedSchedule.getCourseTitle() != null) {
            existingSchedule.setCourseTitle(updatedSchedule.getCourseTitle());
        }
        if (updatedSchedule.getStartDate() != null) {
            existingSchedule.setStartDate(updatedSchedule.getStartDate());
        }
        if (updatedSchedule.getEndDate() != null) {
            existingSchedule.setEndDate(updatedSchedule.getEndDate());
        }
        if (updatedSchedule.getLocation() != null) {
            existingSchedule.setLocation(updatedSchedule.getLocation());
        }

        scheduleRepository.update(id, existingSchedule);
        return existingSchedule;
    }

    public void deactivateSchedule(Long id) {
        scheduleRepository.deactivate(id);
    }


    public void deleteSchedule(Long id) {
        scheduleRepository.delete(id);
    }
}
