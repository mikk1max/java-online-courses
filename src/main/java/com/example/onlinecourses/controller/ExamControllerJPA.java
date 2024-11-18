package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.Exam;
import com.example.onlinecourses.service.ExamServiceJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jpa/exams")
public class ExamControllerJPA {
    @Autowired
    private ExamServiceJPA examService;

    @GetMapping
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/{id}")
    public Exam getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    @PostMapping
    public Exam createExam(@RequestBody Exam exam) {
        return examService.saveExam(exam);
    }

    @PatchMapping("/{id}")
    public Exam updateExam(@PathVariable Long id, @RequestBody Exam updatedExam) {
        return examService.updateExam(id, updatedExam);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivateExam(@PathVariable Long id) {
        examService.deactivateExam(id);
    }

    @DeleteMapping("/{id}")
    public void deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
    }
}
