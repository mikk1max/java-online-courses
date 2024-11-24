package com.example.onlinecourses.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "teacher")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class TeacherDB {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NonNull
        private String name;
        @NonNull
        private int age;
        @NonNull
        private int experience;
        @NonNull
        private double hourlyRate;
        @NonNull
        private Boolean isActive = true;

}
