package com.example.onlinecourses.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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
