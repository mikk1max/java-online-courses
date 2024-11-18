package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Exam;
import com.example.onlinecourses.repository.ExamRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceJPA {
    @Autowired
    private ExamRepositoryJPA examRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAllByIsActiveTrue();
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id).orElse(null);
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Exam updateExam(Long id, Exam updatedExam) {
        return examRepository.findById(id).map(existingExam -> {
            if (updatedExam.getSubject() != null) {
                existingExam.setSubject(updatedExam.getSubject());
            }
            if (updatedExam.getDate() != null) {
                existingExam.setDate(updatedExam.getDate());
            }
            if (updatedExam.getMaxScore() != null) {
                existingExam.setMaxScore(updatedExam.getMaxScore());
            }
            return examRepository.save(existingExam);
        }).orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    public void deactivateExam(Long id) {
        examRepository.findById(id).ifPresent(exam -> {
            exam.setIsActive(false);
            examRepository.save(exam);
        });
    }

    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
}
