package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.*;
import com.example.onlinecourses.repository.CourseRepository;
import com.example.onlinecourses.service.CourseService;
import com.example.onlinecourses.service.ExamService;
import com.example.onlinecourses.service.ScheduleService;
import com.example.onlinecourses.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.http.ResponseEntity;
@Controller
public class StudentHomeController {
    private static List<Course> courses;
    private static List<Course> filtered_courses;
    private static List<Schedule> schedules;
    private static List<Exam> exams;
    private static List<Teacher> teachers;
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final ScheduleService scheduleService;
    private final ExamService examService;
    private final TeacherService teacherService;


    public StudentHomeController(CourseService courseService, CourseRepository courseRepository, ScheduleService scheduleService, ExamService examService, TeacherService teacherService) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.scheduleService = scheduleService;
        this.examService = examService;
        this.teacherService = teacherService;
    }

    @GetMapping("/student/home")
    public String home(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword, Model model) throws Exception {
        //Courses
        courses = courseService.getAllCourses();

        //Keyword filter
        filtered_courses = keyword.isEmpty() ? courses : courseService.filterCourses(courses, keyword);

        // Write to file
        courseService.writeCoursesToFile(courses); // Write to file JavaNIO
        courseService.saveToXMLCourses(courses); // Write to XML

        //Teachers
        teachers = teacherService.getAllTeachers();

        //Schedules
        schedules = scheduleService.getAllSchedules();

        //Exams
        exams = examService.getAllExams();

        try {
            model.addAttribute("courses", courses);
            model.addAttribute("filtered_courses", filtered_courses);
            model.addAttribute("teachers", teachers);
            model.addAttribute("schedules", schedules);
            model.addAttribute("exams", exams);
            model.addAttribute(("keyword"), keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "student";
    }

    @GetMapping("/download/courses.txt")
    public ResponseEntity downloadCoursesTxt() throws Exception {
        return courseService.downloadCoursesTxtFile();
    }

    @GetMapping("/download/courses.xml")
    public ResponseEntity downloadCoursesXml() throws Exception {
        return courseService.downloadCoursesXmlFile();
    }

}
