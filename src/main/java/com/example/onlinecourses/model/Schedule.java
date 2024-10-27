package com.example.onlinecourses.model;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import java.time.Instant;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class Schedule {
    private final @NonNull String courseTitle;
    private final @NonNull String startDate;
    private final @NonNull String endDate;
    private final @NonNull String location;

    public Schedule toEntity() {
        return new Schedule(courseTitle, startDate, endDate, location);
    }
}
