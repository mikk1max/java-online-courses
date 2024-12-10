package com.example.onlinecourses.repository;

import com.example.onlinecourses.model.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Schedule> findAll() {
        String sql = "SELECT * FROM schedule WHERE is_active = true";
        return jdbcTemplate.query(sql, this::mapRowToSchedule);
    }

    @SuppressWarnings("deprecation")
    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[] { id }, this::mapRowToSchedule);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Schedule save(Schedule schedule) {
        String sql = "INSERT INTO schedule (course_title, start_date, end_date, location, is_active) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getCourseTitle(), schedule.getStartDate(), schedule.getEndDate(), schedule.getLocation(), schedule.getIsActive());

        String query = "SELECT * FROM schedule WHERE course_title = ? AND start_date = ? AND end_date = ? AND location = ? AND is_active = ?";
        return jdbcTemplate.queryForObject(query,
                new Object[] { schedule.getCourseTitle(), schedule.getStartDate(), schedule.getEndDate(), schedule.getLocation(), schedule.getIsActive() }, this::mapRowToSchedule);
    }

    public int update(Long id, Schedule updatedSchedule) {
        String sql = "UPDATE schedule SET course_title = ?, start_date = ?, end_date = ?, location = ? WHERE id = ?";
        return jdbcTemplate.update(sql, updatedSchedule.getCourseTitle(), updatedSchedule.getStartDate(), updatedSchedule.getEndDate(), updatedSchedule.getLocation(), id);
    }

    public int deactivate(Long id) {
        String sql = "UPDATE schedule SET is_active = false WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int delete(Long id) { // Permanentne usunięcie
        String sql = "DELETE FROM schedule WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private Schedule mapRowToSchedule(ResultSet rs, int rowNum) throws SQLException {
        return new Schedule(rs.getLong("id"), rs.getString("course_title"), rs.getString("start_date"),
                rs.getString("end_date"), rs.getString("location"), rs.getBoolean("is_active"));
    }
}
