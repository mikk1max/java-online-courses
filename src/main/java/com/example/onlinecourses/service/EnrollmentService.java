package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Course;
import com.example.onlinecourses.model.Enrollment;
import com.example.onlinecourses.model.Student;
import com.example.onlinecourses.repository.CourseRepository;
import com.example.onlinecourses.repository.EnrollmentRepository;
import com.example.onlinecourses.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        student.getCourses().add(course);
        studentRepository.save(student);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Map.Entry<String, String>> getEnrollmentsInfo(List<Enrollment> enrollments) {
        List<Map.Entry<String, String>> enrollmentLines = enrollments.stream()
                .map(enrollment -> {
                    Student student = studentRepository.findById(enrollment.getStudentId())
                            .orElseThrow(() -> new RuntimeException("Student not found"));
                    Course course = courseRepository.findById(enrollment.getCourseId())
                            .orElseThrow(() -> new RuntimeException("Course not found"));
                    return new AbstractMap.SimpleEntry<>(student.getName(), course.getTitle());
                })
                .collect(Collectors.toList());

        return enrollmentLines;
    }



    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id).orElse(null);
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment updateEnrollment(Long id, Enrollment updatedEnrollment) {
        return enrollmentRepository.findById(id).map(existingEnrollment -> {
            if (updatedEnrollment.getStudentId() != null) {
                existingEnrollment.setStudentId(updatedEnrollment.getStudentId());
            }
            if (updatedEnrollment.getCourseId() != null) {
                existingEnrollment.setCourseId(updatedEnrollment.getCourseId());
            }
            return enrollmentRepository.save(existingEnrollment);
        }).orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

}