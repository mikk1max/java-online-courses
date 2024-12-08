package com.example.onlinecourses;
import com.example.onlinecourses.model.Course;
import com.example.onlinecourses.service.CourseService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class CourseServiceTests {
    private CourseService courseService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        courseService = new CourseService();
    }

    @Test
    @DisplayName("writeCoursesToFile - valid courses")
    void testWriteCoursesToFile_ValidCourses() throws IOException {
        // Tworzymy plik w katalogu tymczasowym
        Path filePath = tempDir.resolve("courses.txt");

        // Tworzymy dane testowe
        Course course1 = new Course("Java Basics", "Intro to Java", 10);
        Course course2 = new Course("Spring Boot", "Advanced Spring", 15);

        // Wywołujemy metodę zapisu do pliku
        courseService.writeCoursesToFile(Arrays.asList(course1, course2));

        // Sprawdzamy, czy plik został utworzony
        assertTrue(Files.exists(filePath), "Plik nie został utworzony w oczekiwanym miejscu");

        // Odczytujemy zawartość pliku
        List<String> lines = Files.readAllLines(filePath);

        // Sprawdzamy, czy liczba linii jest zgodna z oczekiwaniami
        assertEquals(2, lines.size(), "Plik powinien zawierać dwie linie");

        // Sprawdzamy, czy zawartość pliku jest zgodna z danymi kursów
        assertTrue(lines.contains("Java Basics-10"), "Plik nie zawiera kursu 'Java Basics-10'");
        assertTrue(lines.contains("Spring Boot-15"), "Plik nie zawiera kursu 'Spring Boot-15'");
    }


    @Test
    @DisplayName("writeCoursesToFile - empty course list")
    void testWriteCoursesToFile_EmptyList() throws IOException {
        // Tworzymy ścieżkę do pliku w katalogu tymczasowym
        Path filePath = tempDir.resolve("courses.txt");

        // Wywołanie metody z pustą listą kursów
        courseService.writeCoursesToFile(Collections.emptyList());

        // Sprawdzamy, że plik nie został utworzony
        assertFalse(Files.exists(filePath), "Plik nie powinien zostać utworzony dla pustej listy kursów");
    }


    @Test
    @DisplayName("readCoursesFromFile - valid file")
    void testReadCoursesFromFile_ValidFile() throws IOException {
        Path filePath = tempDir.resolve("courses.txt");
        Files.write(filePath, Arrays.asList("Java Basics-10", "Spring Boot-15"));

        List<Course> courses = courseService.readCoursesFromFile();

        assertEquals(2, courses.size());
        assertEquals("Java Basics", courses.get(0).getTitle());
        assertEquals(10, courses.get(0).getDuration());
    }

    @Test
    @DisplayName("readCoursesFromFile - empty file")
    void testReadCoursesFromFile_EmptyFile() throws IOException {
        // Tworzymy pusty plik
        Path filePath = tempDir.resolve("courses.txt");
        Files.createFile(filePath);

        // Odczyt kursów z pustego pliku
        List<Course> courses = courseService.readCoursesFromFile();

        // Sprawdzamy, że lista kursów jest pusta
        assertTrue(courses.isEmpty(), "Expected no courses in an empty file.");
    }


    @Test
    @DisplayName("writeCoursesToFileJavaIO - valid courses")
    void testWriteCoursesToFileJavaIO_ValidCourses() throws IOException {
        Path filePath = tempDir.resolve("coursesIO.txt");  // Tworzymy plik w katalogu tymczasowym
        Course course1 = new Course("Java Basics", "Intro to Java", 10);
        Course course2 = new Course("Spring Boot", "Advanced Spring", 15);

        // Zapisujemy kursy do pliku
        courseService.writeCoursesToFileJavaIO(Arrays.asList(course1, course2));

        // Sprawdzamy, czy plik został utworzony
        assertTrue(Files.exists(filePath), "Plik nie został utworzony");

        // Odczytujemy zawartość pliku
        List<String> lines = Files.readAllLines(filePath);

        // Sprawdzamy, czy liczba linii jest zgodna z oczekiwaniami
        assertEquals(2, lines.size(), "Plik powinien zawierać dwie linie");

        // Sprawdzamy, czy zawartość pliku jest zgodna z danymi kursów
        assertTrue(lines.contains("Java Basics-10"), "Plik nie zawiera kursu 'Java Basics-10'");
        assertTrue(lines.contains("Spring Boot-15"), "Plik nie zawiera kursu 'Spring Boot-15'");
    }



    @Test
    @DisplayName("readCoursesFromFileJavaIO - valid and invalid data")
    void testReadCoursesFromFileJavaIO_ValidAndInvalidData() throws IOException {
        // Tworzymy plik z prawidłowymi i błędnymi danymi
        Path filePath = tempDir.resolve("courses.txt");
        List<String> lines = Arrays.asList(
                "Java Basics-10",     // Prawidłowy wpis
                "Spring Boot-15",     // Prawidłowy wpis
                "Invalid Line",         // Błędny wpis (brak formatu)
                "React - abc"        // Błędny wpis (nieprawidłowa liczba)

        );
        Files.write(filePath, lines);

        // Wywołanie metody
        List<Course> courses = courseService.readCoursesFromFileJavaIO();

        // Wypisujemy zawartość kursów dla diagnozy
        courses.forEach(course -> System.out.println("Course: " + course.getTitle() + ", Duration: " + course.getDuration()));

        // Sprawdzamy, czy zostały poprawnie wczytane tylko prawidłowe dane
        assertNotNull(courses, "Lista kursów nie powinna być null");
        assertEquals(2, courses.size(), "Liczba kursów powinna wynosić 2 (prawidłowe dane)");

        // Sprawdzamy, czy zawartość kursów jest zgodna z danymi z pliku
        assertEquals("Java Basics", courses.get(0).getTitle(), "Tytuł kursu jest niepoprawny");
        assertEquals(10, courses.get(0).getDuration(), "Czas trwania kursu jest niepoprawny");

        assertEquals("Spring Boot", courses.get(1).getTitle(), "Tytuł kursu jest niepoprawny");
        assertEquals(15, courses.get(1).getDuration(), "Czas trwania kursu jest niepoprawny");

        // Sprawdzamy, czy błędne dane zostały pominięte (w tym przypadku "Invalid Line" i "React - abc")
        assertEquals(2, courses.size(), "Błędne dane powinny zostać pominięte");
    }




    @Test
    @DisplayName("saveToXMLCourses - valid courses")
    void testSaveToXMLCourses_ValidCourses() {
        // Ustal stałą ścieżkę do pliku w zasobach projektu (albo w odpowiednim katalogu)
        Path filePath = Paths.get("src", "main", "resources", "data", "courses.xml");

        // Tworzymy przykładowy kurs
        Course course1 = new Course("Java Basics", "Intro to Java", 10);

        // Wywołujemy metodę zapisującą kurs do pliku XML
        courseService.saveToXMLCourses(Collections.singletonList(course1));

        // Sprawdzamy, czy plik został utworzony
        assertTrue(Files.exists(filePath), "Plik courses.xml nie został utworzony");
    }


    @Test
    @DisplayName("readCourseFromXML - valid XML")
    void testReadCourseFromXML_ValidXML() throws IOException {
        Path filePath = tempDir.resolve("courses.xml");
        Files.write(filePath, Arrays.asList(
                "<?xml version=\"1.0\"?>",
                "<courses>",
                "  <course>",
                "    <title>Java Basics</title>",
                "    <description>Intro to Java</description>",
                "    <duration>10</duration>",
                "  </course>",
                "</courses>"
        ));

        List<String> courseData = courseService.readCourseFromXML();

        assertEquals(1, courseData.size());
        assertTrue(courseData.get(0).contains("Java Basics"));
    }

    @Test
    @DisplayName("CoursesFromXMLtoObjects - valid XML")
    void testCoursesFromXMLtoObjects_ValidXML() throws IOException {
        Path filePath = tempDir.resolve("courses.xml");
        Files.write(filePath, Arrays.asList(
                "<?xml version=\"1.0\"?>",
                "<courses>",
                "  <course>",
                "    <title>Java Basics</title>",
                "    <description>Intro to Java</description>",
                "    <duration>10</duration>",
                "  </course>",
                "</courses>"
        ));

        List<Course> courses = courseService.CoursesFromXMLtoObjects();

        assertEquals(1, courses.size());
        assertEquals("Java Basics", courses.get(0).getTitle());
    }
}
