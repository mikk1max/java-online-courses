package com.example.onlinecourses.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Admin extends User {
    @NonNull
    private String name;
    @NonNull
    private int age;
}
