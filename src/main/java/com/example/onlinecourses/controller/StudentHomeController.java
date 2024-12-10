package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.*;
import com.example.onlinecourses.service.CourseService;
import com.example.onlinecourses.service.EnrollmentService;
import com.example.onlinecourses.service.InitializeService;
import com.example.onlinecourses.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class StudentHomeController {
        private static List<Course> courses;

        private final InitializeService initializeService;
        private final CourseService courseService;

    public StudentHomeController(InitializeService initializeService, CourseService courseService) {
            this.initializeService = initializeService;
            this.courseService = courseService;
        }

        @GetMapping("/student/home")
        public String home (Model model) throws Exception {
            courses = initializeService.initializeCourses();


            //!Courses part
            // Filter courses with Java in the title
            Stream<Course> streamCoursesWithJava = courses.stream()
                    .filter(course -> course.getTitle().contains("Java"));

            // *Using Java NIO
            courseService.writeCoursesToFile(courses); // Write to file JavaNIO

            courseService.saveToXMLCourses(courses); // Write to XML


            // !LAB 3 all classes

            Schedule schedule = new Schedule("Advanced java", "15/04/2024", "15/10/2024", "Lecture hall 201");

            // * Exam
            Exam exam = new Exam("Java", LocalDate.of(2024, 12, 16));
            exam.setMaxScore(100);


            try {
                // !LAB 1-2
                model.addAttribute("courses", courses);
                model.addAttribute("streamCoursesWithJava", streamCoursesWithJava);
                model.addAttribute("schedule", schedule);
                model.addAttribute("exam", exam);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "student";
        }


        public static Course findCourseByTitle (String title){
            return courses.stream()
                    .filter(course -> course.getTitle().equalsIgnoreCase(title))
                    .findFirst()
                    .orElse(null);
        }

    }
