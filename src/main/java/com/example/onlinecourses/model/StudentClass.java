package com.example.onlinecourses.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentClass {
    private List<Student> students;
    private Course course;
    private Teacher teacher;
}
