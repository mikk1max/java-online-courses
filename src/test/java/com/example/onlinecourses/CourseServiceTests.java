package com.example.onlinecourses;

import com.example.onlinecourses.model.Course;
import com.example.onlinecourses.repository.CourseRepository;
import com.example.onlinecourses.service.CourseService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTests {
    private CourseService courseService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        CourseRepository courseRepository = mock(CourseRepository.class);
        courseService = new CourseService(courseRepository);
    }



    @Test
    @DisplayName("writeCoursesToFile - empty course list")
    void testWriteCoursesToFile_EmptyList() throws IOException {
        Path filePath = tempDir.resolve("courses.txt");

        courseService.saveToFile(Collections.emptyList());

        assertFalse(Files.exists(filePath), "Plik nie powinien zostać utworzony dla pustej listy kursów");
    }

    @Test
    @DisplayName("saveToXMLCourses - valid courses")
    void testSaveToXMLCourses_ValidCourses() {
        Path filePath = Paths.get("src", "main", "resources", "data", "courses.xml");

        Course course1 = new Course("Java Basics", "Intro to Java", 10);

        courseService.saveToXML(Collections.singletonList(course1));

        assertTrue(Files.exists(filePath), "Plik courses.xml nie został utworzony");
    }




}