package com.example.onlinecourses.repository;

import com.example.onlinecourses.model.TeacherDB;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TeacherRepository {
    private final JdbcTemplate jdbcTemplate;

    // Konstruktor, który przyjmuje JdbcTemplate jako argument
    public TeacherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Metoda do znajdowania wszystkich aktywnych nauczycieli
    public List<TeacherDB> findAll() {
        String sql = "SELECT * FROM teacher WHERE is_active = true"; // Zmiana na wybór tylko aktywnych nauczycieli
        return jdbcTemplate.query(sql, this::mapRowToTeacher);
    }

    // Metoda do znajdowania nauczyciela według identyfikatora
    public TeacherDB findById(Long id) {
        String sql = "SELECT * FROM teacher WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToTeacher);
        } catch (EmptyResultDataAccessException e) {
            return null; // Zwraca null, jeśli nauczyciel o danym identyfikatorze nie został znaleziony
        }
    }

    // Metoda do zapisywania nowego nauczyciela
    public TeacherDB save(TeacherDB teacher) {
        String sql = "INSERT INTO teacher (name, age, experience, hourly_rate, is_active) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, teacher.getName(), teacher.getAge(), teacher.getExperience(), teacher.getHourlyRate(), teacher.getIsActive());

        String query = "SELECT * FROM teacher WHERE name = ? AND age = ? AND experience = ? AND hourly_rate = ? AND is_active = ?";
        return jdbcTemplate.queryForObject(query,
                new Object[]{teacher.getName(), teacher.getAge(), teacher.getExperience(), teacher.getHourlyRate(), teacher.getIsActive()},
                this::mapRowToTeacher);
    }

    // Metoda do aktualizacji danych nauczyciela
    public int update(Long id, TeacherDB updatedTeacher) {
        String sql = "UPDATE teacher SET name = ?, age = ?, experience = ?, hourly_rate = ? WHERE id = ?";
        return jdbcTemplate.update(sql, updatedTeacher.getName(), updatedTeacher.getAge(), updatedTeacher.getExperience(), updatedTeacher.getHourlyRate(), id);
    }

    // Metoda do dezaktywacji nauczyciela
    public int deactivate(Long id) {
        String sql = "UPDATE teacher SET is_active = false WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Metoda do usuwania nauczyciela
    public int delete(Long id) {
        String sql = "DELETE FROM teacher WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Metoda do mapowania wiersza wyniku na obiekt TeacherDB
    private TeacherDB mapRowToTeacher(ResultSet rs, int rowNum) throws SQLException {
        return new TeacherDB(rs.getLong("id"), rs.getString("name"), rs.getInt("age"),
                rs.getInt("experience"), rs.getDouble("hourly_rate"), rs.getBoolean("is_active"));
    }
}
