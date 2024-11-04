package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.TeacherDB;
import com.example.onlinecourses.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public List<TeacherDB> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDB> getTeacherById(@PathVariable Long id) {
        TeacherDB teacher = teacherRepository.findById(id);
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TeacherDB> createTeacher(@RequestBody TeacherDB teacher) {
        TeacherDB savedTeacher = teacherRepository.save(teacher);
        return ResponseEntity.ok(savedTeacher);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeacherDB> updateTeacher(@PathVariable Long id, @RequestBody TeacherDB updatedTeacher) {
        int rowsAffected = teacherRepository.update(id, updatedTeacher);
        if (rowsAffected > 0) {
            return ResponseEntity.ok(updatedTeacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateTeacher(@PathVariable Long id) {
        int rowsAffected = teacherRepository.deactivate(id);
        if (rowsAffected > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        int rowsAffected = teacherRepository.delete(id);
        if (rowsAffected > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
