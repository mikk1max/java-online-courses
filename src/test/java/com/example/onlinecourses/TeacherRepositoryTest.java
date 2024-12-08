package com.example.onlinecourses;

import com.example.onlinecourses.model.TeacherDB;
import com.example.onlinecourses.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TeacherRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnListOfActiveTeachers() {
        // Arrange
        TeacherDB teacher1 = new TeacherDB(1L, "John Doe", 30, 5, 50.0, true);
        TeacherDB teacher2 = new TeacherDB(2L, "Jane Smith", 45, 20, 75.0, true);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(List.of(teacher1, teacher2));

        // Act
        List<TeacherDB> teachers = teacherRepository.findAll();

        // Assert
        assertNotNull(teachers);
        assertEquals(2, teachers.size());
        assertEquals("John Doe", teachers.get(0).getName());
    }

    @Test
    void findById_ShouldReturnTeacher_WhenTeacherExists() {
        // Arrange
        TeacherDB teacher = new TeacherDB(1L, "John Doe", 30, 5, 50.0, true);
        when(jdbcTemplate.queryForObject(
                anyString(),
                any(Object[].class),
                any(RowMapper.class)
        )).thenReturn(teacher);

        // Act
        TeacherDB result = teacherRepository.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void findById_ShouldReturnNull_WhenTeacherDoesNotExist() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenThrow(new org.springframework.dao.EmptyResultDataAccessException(1));

        // Act
        TeacherDB result = teacherRepository.findById(99L);

        // Assert
        assertNull(result);
    }

    @Test
    void save_ShouldInsertAndReturnSavedTeacher() {
        // Arrange
        TeacherDB teacher = new TeacherDB(null, "John Doe", 30, 5, 50.0, true);
        TeacherDB savedTeacher = new TeacherDB(1L, "John Doe", 30, 5, 50.0, true);
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any())).thenReturn(1);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(savedTeacher);

        // Act
        TeacherDB result = teacherRepository.save(teacher);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void save_ShouldThrowException_WhenInsertFails() {
        // Arrange
        TeacherDB teacher = new TeacherDB(null, "John Doe", 30, 5, 50.0, true);
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any())).thenThrow(new RuntimeException("Insert failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> teacherRepository.save(teacher));
    }


    @Test
    void deactivate_ShouldReturnNumberOfUpdatedRows() {
        // Arrange
        when(jdbcTemplate.update(anyString(), Optional.ofNullable(any()))).thenReturn(1);

        // Act
        int rowsUpdated = teacherRepository.deactivate(1L);

        // Assert
        assertEquals(1, rowsUpdated);
    }

    @Test
    void delete_ShouldReturnNumberOfDeletedRows() {
        // Arrange
        when(jdbcTemplate.update(anyString(), Optional.ofNullable(any()))).thenReturn(1);

        // Act
        int rowsDeleted = teacherRepository.delete(1L);

        // Assert
        assertEquals(1, rowsDeleted);
    }

    @Test
    void delete_ShouldReturnZero_WhenNoRowsDeleted() {
        // Arrange
        when(jdbcTemplate.update(anyString(), Optional.ofNullable(any()))).thenReturn(0);

        // Act
        int rowsDeleted = teacherRepository.delete(99L);

        // Assert
        assertEquals(0, rowsDeleted);
    }
}
