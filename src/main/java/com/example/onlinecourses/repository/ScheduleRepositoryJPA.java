package com.example.onlinecourses.repository;

import com.example.onlinecourses.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepositoryJPA extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByIsActiveTrue(); // Pobiera tylko aktywne harmonogramy
}

