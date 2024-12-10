package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Course;
import com.example.onlinecourses.model.Enrollment;
import com.example.onlinecourses.model.Student;
import com.example.onlinecourses.repository.CourseRepository;
import com.example.onlinecourses.repository.EnrollmentRepository;
import com.example.onlinecourses.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(StudentRepository studentRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Map.Entry<String, String>> getEnrollmentsInfo(List<Enrollment> enrollments) {
        return enrollments.stream()
                .map(enrollment -> {
                    Student student = studentRepository.findById(enrollment.getStudentId())
                            .orElseThrow(() -> new IllegalArgumentException("Student not found"));
                    Course course = courseRepository.findById(enrollment.getCourseId())
                            .orElseThrow(() -> new IllegalArgumentException("Course not found"));
                    return new AbstractMap.SimpleEntry<>(student.getName(), course.getTitle());
                })
                .collect(Collectors.toList());
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Enrollment with ID " + id + " not found"));
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment updateEnrollment(Long id, Enrollment updatedEnrollment) {
        return enrollmentRepository.findById(id).map(existingEnrollment -> {
            existingEnrollment.setStudentId(updatedEnrollment.getStudentId());
            existingEnrollment.setCourseId(updatedEnrollment.getCourseId());
            return enrollmentRepository.save(existingEnrollment);
        }).orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

}