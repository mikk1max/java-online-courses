package com.example.onlinecourses;

import com.example.onlinecourses.model.Exam;
import com.example.onlinecourses.repository.ExamRepository;
import com.example.onlinecourses.service.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

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
    public void testGetAllExams() {
        // Przygotowanie danych testowych
        Exam exam1 = new Exam(1L, "Math", java.time.LocalDate.now(), 100, true);
        Exam exam2 = new Exam(2L, "History", java.time.LocalDate.now(), 80, true);

        List<Exam> exams = Arrays.asList(exam1, exam2);
        when(examRepository.findAll()).thenReturn(exams);

        // Testowanie metody
        List<Exam> result = examService.getAllExams();

        // Assercje
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).getSubject());
        verify(examRepository, times(1)).findAll();
    }

    @Test
    public void testGetExamById() {
        // Przygotowanie danych testowych
        Exam exam = new Exam(1L, "Math", java.time.LocalDate.now(), 100, true);

        when(examRepository.findById(1L)).thenReturn(exam);

        // Testowanie metody
        Exam result = examService.getExamById(1L);

        // Assercje
        assertNotNull(result);
        assertEquals("Math", result.getSubject());
        verify(examRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetExamById_NotFound() {
        when(examRepository.findById(1L)).thenReturn(null);

        // Testowanie metody, gdy egzamin nie istnieje
        Exam result = examService.getExamById(1L);

        // Assercje
        assertNull(result);
        verify(examRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveExam() {
        // Przygotowanie danych testowych
        Exam exam = new Exam(1L, "Math", java.time.LocalDate.now(), 100, true);

        when(examRepository.save(exam)).thenReturn(exam);

        // Testowanie metody
        Exam result = examService.saveExam(exam);

        // Assercje
        assertNotNull(result);
        assertEquals("Math", result.getSubject());
        verify(examRepository, times(1)).save(exam);
    }

    @Test
    public void testUpdateExam() {
        // Przygotowanie danych testowych
        Exam existingExam = new Exam(1L, "Math", java.time.LocalDate.now(), 100, true);
        Exam updatedExam = new Exam(1L, "Math", java.time.LocalDate.now(), 120, true);

        when(examRepository.findById(1L)).thenReturn(existingExam);
        when(examRepository.update(1L, updatedExam)).thenReturn(1);

        // Testowanie metody
        Exam result = examService.updateExam(1L, updatedExam);

        // Assercje
        assertNotNull(result);
        assertEquals(120, result.getMaxScore());
        verify(examRepository, times(1)).findById(1L);
        verify(examRepository, times(1)).update(1L, updatedExam);
    }

    @Test
    public void testUpdateExam_NotFound() {
        Exam updatedExam = new Exam(1L, "Math", java.time.LocalDate.now(), 120, true);

        when(examRepository.findById(1L)).thenReturn(null);

        // Testowanie metody, gdy egzamin nie istnieje
        Exception exception = assertThrows(RuntimeException.class, () -> {
            examService.updateExam(1L, updatedExam);
        });

        // Assercje
        assertNotNull(exception);
    }

    @Test
    public void testDeactivateExam() {
        Long examId = 1L;

        when(examRepository.deactivate(examId)).thenReturn(1);

        // Testowanie metody
        examService.deactivateExam(examId);

        // Assercje
        verify(examRepository, times(1)).deactivate(examId);
    }
}
