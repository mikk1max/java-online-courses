package com.example.onlinecourses.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class ExamSession {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}