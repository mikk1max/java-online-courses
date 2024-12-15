package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Exam;
import com.example.onlinecourses.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public List<Exam> getAllExams() {
        return examRepository.findAllByIsActiveTrue();
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exam with ID " + id + " not found"));
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Exam updateExam(Long id, Exam updatedExam) {
        return examRepository.findById(id).map(existingExam -> {
            existingExam.setSubject(updatedExam.getSubject());
            existingExam.setDate(updatedExam.getDate());
            if (updatedExam.getMaxScore() != null &&  updatedExam.getMaxScore() > 0) {
                existingExam.setMaxScore(updatedExam.getMaxScore());
            }
            return examRepository.save(existingExam);
        }).orElseThrow(() -> new IllegalArgumentException("Exam with ID " + id + " not found"));
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
