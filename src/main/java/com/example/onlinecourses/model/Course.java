package com.example.onlinecourses.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private int duration;
    @NonNull
    private Boolean isActive = true;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}
