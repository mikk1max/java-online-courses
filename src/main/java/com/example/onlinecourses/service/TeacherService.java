package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Teacher;
import com.example.onlinecourses.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAllByIsActiveTrue();
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Teacher with ID " + id + " not found"));
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        return teacherRepository.findById(id).map(existingTeacher -> {

            existingTeacher.setName(updatedTeacher.getName());
            existingTeacher.setExperience(updatedTeacher.getExperience());
            existingTeacher.setAge(updatedTeacher.getAge());
            existingTeacher.setHourlyRate(updatedTeacher.getHourlyRate());
            existingTeacher.setIsActive(updatedTeacher.getIsActive());
            return teacherRepository.save(existingTeacher);
        }).orElseThrow(() -> new IllegalArgumentException("Teacher with ID " + id + " not found"));
    }

    public void deactivateTeacher(Long id) {
        teacherRepository.findById(id).ifPresent(teacher -> {
            teacher.setIsActive(false);
            teacherRepository.save(teacher);
        });
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}