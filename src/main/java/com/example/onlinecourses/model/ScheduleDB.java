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
public class ScheduleDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String courseTitle;
    @NonNull
    private String startDate;
    @NonNull
    private String endDate;
    @NonNull
    private String location;
    @NonNull
    private Boolean isActive = true;
}
