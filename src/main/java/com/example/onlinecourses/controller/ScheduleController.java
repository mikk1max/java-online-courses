package com.example.onlinecourses.controller;


import com.example.onlinecourses.model.Exam;
import com.example.onlinecourses.model.ScheduleDB;
import com.example.onlinecourses.service.ExamService;
import com.example.onlinecourses.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public List<ScheduleDB> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public ScheduleDB getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @PostMapping
    public ScheduleDB createSchedule(@RequestBody ScheduleDB schedule) {
        return scheduleService.saveSchedule(schedule);
    }

    @PatchMapping("/{id}")
    public ScheduleDB updateSchedule(@PathVariable Long id, @RequestBody ScheduleDB updatedSchedule) {
        return scheduleService.updateSchedule(id, updatedSchedule);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivateSchedule(@PathVariable Long id) {
        scheduleService.deactivateSchedule(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}
