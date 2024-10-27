package com.example.onlinecourses.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    private int id;
    private String title;
    private String description;
    private Teacher assignedBy;
    private boolean isCompleted;

}
