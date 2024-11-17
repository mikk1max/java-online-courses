package com.example.onlinecourses.repository;

import com.example.onlinecourses.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepositoryJPA extends JpaRepository<Exam, Long> {
    List<Exam> findAllByIsActiveTrue(); // Pobiera tylko aktywne egzaminy
}
