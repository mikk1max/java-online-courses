package com.example.onlinecourses.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private String CourseTitle;
    private int score;
    private String comment;
    private String date;
}
