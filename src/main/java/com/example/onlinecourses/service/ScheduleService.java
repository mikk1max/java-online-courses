package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Schedule;
import com.example.onlinecourses.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAllByIsActiveTrue();
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule with ID " + id + " not found"));
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        return scheduleRepository.findById(id).map(existingSchedule -> {
            existingSchedule.setCourseTitle(updatedSchedule.getCourseTitle());
            existingSchedule.setStartDate(updatedSchedule.getStartDate());
            existingSchedule.setEndDate(updatedSchedule.getEndDate());
            existingSchedule.setLocation(updatedSchedule.getLocation());
            return scheduleRepository.save(existingSchedule);
        }).orElseThrow(() -> new IllegalArgumentException("Schedule with ID " + id + " not found"));
    }

    public void deactivateSchedule(Long id) {
        scheduleRepository.findById(id).ifPresent(schedule -> {
            schedule.setIsActive(false);
            scheduleRepository.save(schedule);
        });
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}
