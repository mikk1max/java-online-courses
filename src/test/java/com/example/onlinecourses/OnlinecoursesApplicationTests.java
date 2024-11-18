package com.example.onlinecourses;

import com.example.onlinecourses.controller.HomeController;

import com.example.onlinecourses.model.*;
import com.example.onlinecourses.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class OnlinecoursesApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private InitializeService initializeService;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private CourseService courseService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private HomeController homeController;

    private List<Course> courses;
    private List<Student> students;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        courses = Stream.of(
                new Course("Java Programming", "Learn Java", 40),
                new Course("Python Programming", "Learn Python", 35)
        ).collect(Collectors.toList());

        students = Stream.of(
                new Student("John Doe", 25),
                new Student("Jane Smith", 30),
                new Student("Alice Johnson", 35)
        ).collect(Collectors.toList());


        when(initializeService.initializeCourses()).thenReturn(courses);
        when(initializeService.initializeStudents()).thenReturn(students);
    }

    @Test
    public void testHomePage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()) // Проверяем, что страница доступна
                .andExpect(view().name("index")); // Проверяем, что вернулся правильный шаблон (view)
    }
}

