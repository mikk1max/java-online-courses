package com.example.onlinecourses.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(name = "student_id")
    private Long studentId;
    @NonNull
    @Column(name = "course_id")
    private Long courseId;
}

