package com.example.onlinecourses;

import com.example.onlinecourses.model.Exam;
import com.example.onlinecourses.repository.ExamRepository;
import com.example.onlinecourses.service.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @InjectMocks
    private ExamService examService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testGetExamById() {
        Exam exam = new Exam(1L, "Math", java.time.LocalDate.now(), 100, true);

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        Exam result = examService.getExamById(1L);

        assertNotNull(result);
        assertEquals("Math", result.getSubject());
        verify(examRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetExamById_NotFound() {
        when(examRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examService.getExamById(1L);
        });

        assertEquals("Exam with ID " + 1L + " not found", exception.getMessage());

        verify(examRepository, times(1)).findById(1L);
    }


    @Test
    public void testSaveExam() {
        Exam exam = new Exam(1L, "Math", java.time.LocalDate.now(), 100, true);
        when(examRepository.save(exam)).thenReturn(exam);

        Exam result = examService.saveExam(exam);

        assertNotNull(result);
        assertEquals("Math", result.getSubject());
        verify(examRepository, times(1)).save(exam);
    }

    @Test
    public void testUpdateExam() {
        Exam existingExam = new Exam(1L, "Math", java.time.LocalDate.now(), 100, true);
        Exam updatedExam = new Exam(1L, "Math", java.time.LocalDate.now(), 120, true);

        when(examRepository.findById(1L)).thenReturn(Optional.of(existingExam));
        when(examRepository.save(updatedExam)).thenReturn(updatedExam);

        Exam result = examService.updateExam(1L, updatedExam);

        assertNotNull(result);
        assertEquals(120, result.getMaxScore());
        verify(examRepository, times(1)).findById(1L);
        verify(examRepository, times(1)).save(updatedExam);
    }

    @Test
    public void testUpdateExam_NotFound() {
        Exam updatedExam = new Exam(1L, "Math", java.time.LocalDate.now(), 120, true);

        when(examRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            examService.updateExam(1L, updatedExam);
        });

        assertNotNull(exception);
    }


}