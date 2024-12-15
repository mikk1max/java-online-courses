package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.*;
import com.example.onlinecourses.service.CourseService;
import com.example.onlinecourses.service.ExamService;
import com.example.onlinecourses.service.ScheduleService;
import com.example.onlinecourses.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Controller
public class StudentHomeController {
    private final CourseService courseService;
    private final ScheduleService scheduleService;
    private final ExamService examService;
    private final TeacherService teacherService;

    public StudentHomeController(CourseService courseService, ScheduleService scheduleService, ExamService examService, TeacherService teacherService) {
        this.courseService = courseService;
        this.scheduleService = scheduleService;
        this.examService = examService;
        this.teacherService = teacherService;
    }

    @GetMapping("/student/home")
    public String home(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword, Model model) {
        List<Course> courses = courseService.getAllCourses();
        List<Course> filtered_courses = keyword.isEmpty() ? courses : courseService.getFilterCourses(courses, keyword);

        courseService.saveToFile(courses);
        courseService.saveToXML(courses);

        try {
            model.addAttribute("courses", courses);
            model.addAttribute("filtered_courses", filtered_courses);
            model.addAttribute("teachers", teacherService.getAllTeachers());
            model.addAttribute("schedules", scheduleService.getAllSchedules());
            model.addAttribute("exams", examService.getAllExams());
            model.addAttribute(("keyword"), keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "student";
    }

    @GetMapping("/student/home/download/courses.txt")
    public ResponseEntity<?> downloadCoursesTxt() throws Exception {
        return courseService.downloadTxtFile();
    }

    @GetMapping("/student/home/download/courses.xml")
    public ResponseEntity<?> downloadCoursesXml() throws Exception {
        return courseService.downloadXmlFile();
    }

}
