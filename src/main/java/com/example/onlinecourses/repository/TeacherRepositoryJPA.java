package com.example.onlinecourses.repository;

import com.example.onlinecourses.model.Exam;
import com.example.onlinecourses.model.TeacherDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepositoryJPA extends JpaRepository<TeacherDB, Long> {
    List<TeacherDB> findAllByIsActiveTrue();
}
