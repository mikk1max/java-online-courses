package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Exam;
import com.example.onlinecourses.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id);
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Exam updateExam(Long id, Exam updatedExam) {
        Exam existingExam = examRepository.findById(id);
        if (existingExam == null) {
            throw new RuntimeException("Exam not found");
        }

        if (updatedExam.getSubject() != null) {
            existingExam.setSubject(updatedExam.getSubject());
        }
        if (updatedExam.getDate() != null) {
            existingExam.setDate(updatedExam.getDate());
        }
        if (updatedExam.getMaxScore() != null) {
            existingExam.setMaxScore(updatedExam.getMaxScore());
        }

        examRepository.update(id, existingExam);
        return existingExam;
    }

    public void deactivateExam(Long id) {
        examRepository.deactivate(id);
    }


    public void deleteExam(Long id) {
        examRepository.delete(id);
    }
}
