package com.example.onlinecourses.controller;
import com.example.onlinecourses.model.TeacherDB;
import com.example.onlinecourses.service.TeacherServiceJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jpa/teachers")
public class TeacherControllerJPA {
    @Autowired
    private TeacherServiceJPA teacherService;

    @GetMapping
    public List<TeacherDB> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDB getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    public TeacherDB createTeacher(@RequestBody TeacherDB teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @PatchMapping("/{id}")
    public TeacherDB updateTeacher(@PathVariable Long id, @RequestBody TeacherDB updatedTeacher) {
        return teacherService.updateTeacher(id, updatedTeacher);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivateTeacher(@PathVariable Long id) {
        teacherService.deactivateTeacher(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }
}
