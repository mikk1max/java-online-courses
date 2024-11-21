package com.example.onlinecourses.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "schedule")
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
