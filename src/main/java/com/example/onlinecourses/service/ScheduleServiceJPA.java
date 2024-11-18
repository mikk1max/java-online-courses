package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Exam;
import com.example.onlinecourses.model.ScheduleDB;
import com.example.onlinecourses.repository.ExamRepositoryJPA;
import com.example.onlinecourses.repository.ScheduleRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceJPA {
    @Autowired
    private ScheduleRepositoryJPA scheduleRepositoryJPA;

    public List<ScheduleDB> getAllSchedules() {
        return scheduleRepositoryJPA.findAllByIsActiveTrue();
    }

    public ScheduleDB getScheduleById(Long id) {
        return scheduleRepositoryJPA.findById(id).orElse(null);
    }

    public ScheduleDB saveSchedule(ScheduleDB schedule) {
        return scheduleRepositoryJPA.save(schedule);
    }

    public ScheduleDB updateSchedule(Long id, ScheduleDB updatedSchedule) {
        return scheduleRepositoryJPA.findById(id).map(existingSchedule -> {
            existingSchedule.setCourseTitle(updatedSchedule.getCourseTitle());
            existingSchedule.setStartDate(updatedSchedule.getStartDate());
            existingSchedule.setEndDate(updatedSchedule.getEndDate());
            existingSchedule.setLocation(updatedSchedule.getLocation());
            return scheduleRepositoryJPA.save(existingSchedule);
        }).orElseThrow(() -> new RuntimeException("Shedule not found"));
    }

    public void deactivateSchedule(Long id) {
        scheduleRepositoryJPA.findById(id).ifPresent(schedule -> {
            schedule.setIsActive(false);
            scheduleRepositoryJPA.save(schedule);
        });
    }

    public void deleteSchedule(Long id) {
        scheduleRepositoryJPA.deleteById(id);
    }
}
