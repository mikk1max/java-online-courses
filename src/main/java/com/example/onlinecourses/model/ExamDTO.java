package com.example.onlinecourses.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {
    private Long id;
    private String subject;
    private LocalDate date;
    private Integer maxScore;
    private boolean isActive;

    // Save the original state of the object
    private Exam originalState;

    // Constructor to map Exam entity to ExamDTO
    public ExamDTO(Exam exam) {
        this.id = exam.getId();
        this.subject = exam.getSubject();
        this.date = exam.getDate();
        this.maxScore = exam.getMaxScore();
        this.originalState = new Exam(exam.getId(), exam.getSubject(), exam.getDate(), exam.getMaxScore(), exam.getIsActive());
    }

    public ExamDTO(ExamDTO examDTO) {
        this.id = examDTO.id;
        this.subject = examDTO.subject;
        this.date = examDTO.date;
        this.maxScore = examDTO.maxScore;
        this.isActive = examDTO.isActive;
        this.originalState = examDTO.originalState; // If you want to keep the original state reference
    }

    // Method to update the state of the object
    public void update(String newSubject, LocalDate newDate, Integer newMaxScore) {
        // Check if originalState is already set
        if (originalState == null) {
            // Save the original state before any updates
            originalState = new Exam(id, subject, date, maxScore, isActive);
        }
        this.subject = newSubject;
        this.date = newDate;
        this.maxScore = newMaxScore;
    }

    // Method to roll back the state of the object
    public void rollback() {
        if (originalState != null) {
            this.id = originalState.getId();
            this.subject = originalState.getSubject();
            this.date = originalState.getDate();
            this.maxScore = originalState.getMaxScore();
        }
    }

    // Map ExamDTO to Exam entity
    public Exam toExam() {
        return new Exam(id, subject, date, maxScore, isActive);
    }
}
