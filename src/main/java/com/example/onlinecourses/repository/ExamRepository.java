package com.example.onlinecourses.repository;

import com.example.onlinecourses.model.Exam;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExamRepository {
    private final JdbcTemplate jdbcTemplate;

    public ExamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Exam> findAll() {
        String sql = "SELECT * FROM exam WHERE is_active = true"; // Zmiana na selekcję tylko aktywnych egzaminów
        return jdbcTemplate.query(sql, this::mapRowToExam);
    }

    @SuppressWarnings("deprecation")
    public Exam findById(Long id) {
        String sql = "SELECT * FROM exam WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[] { id }, this::mapRowToExam);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public Exam save(Exam exam) {
        String sql = "INSERT INTO exam (subject, date, max_score, is_active) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, exam.getSubject(), exam.getDate(), exam.getMaxScore(), exam.getIsActive());

        String query = "SELECT * FROM exam WHERE subject = ? AND date = ? AND max_score = ? AND is_active = ?";
        return jdbcTemplate.queryForObject(query,
                new Object[] { exam.getSubject(), exam.getDate(), exam.getMaxScore(), exam.getIsActive() },
                this::mapRowToExam);
    }

    public int update(Long id, Exam updatedExam) {
        String sql = "UPDATE exam SET subject = ?, date = ?, max_score = ? WHERE id = ?";
        return jdbcTemplate.update(sql, updatedExam.getSubject(), updatedExam.getDate(), updatedExam.getMaxScore(), id);
    }

    public int deactivate(Long id) {
        String sql = "UPDATE exam SET is_active = false WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int delete(Long id) { // Permanentne usunięcie
        String sql = "DELETE FROM exam WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private Exam mapRowToExam(ResultSet rs, int rowNum) throws SQLException {
        return new Exam(rs.getLong("id"), rs.getString("subject"), rs.getDate("date").toLocalDate(),
                rs.getInt("max_score"), rs.getBoolean("is_active"));
    }
}
