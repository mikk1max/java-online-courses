package com.example.onlinecourses.repository;

import com.example.onlinecourses.model.ScheduleDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepositoryJPA extends JpaRepository<ScheduleDB, Long> {
    List<ScheduleDB> findAllByIsActiveTrue(); // Pobiera tylko aktywne harmonogramy
}

