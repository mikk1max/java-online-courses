package com.example.onlinecourses.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Teacher extends User {
    @NonNull
    private String name;

    @NonNull
    private int age;

    @NonNull
    private int experience;

    @NonNull
    private double hourlyRate;
}
