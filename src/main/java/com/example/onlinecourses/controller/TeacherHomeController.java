package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.*;
import com.example.onlinecourses.repository.CourseRepository;
import com.example.onlinecourses.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class TeacherHomeController {
    private final ExamService examService;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    public TeacherHomeController(ExamService examService, StudentService studentService, EnrollmentService enrollmentService) {
        this.examService = examService;
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/teacher/home")
    public String home(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword, Model model) throws Exception {
        List<Student> students = studentService.getAllStudents();
        List<Student> filtered_students = keyword.isEmpty() ? students : studentService.filterStudents(students, keyword);
        List<Map.Entry<String, String>> enrollmentInfo = enrollmentService.getEnrollmentsInfo(enrollmentService.getAllEnrollments());

        studentService.saveToFile(students);
        studentService.saveToXML(students);

        try {
            model.addAttribute("students", students);
            model.addAttribute("filtered_students", filtered_students);
            model.addAttribute("enrollmentInfo", enrollmentInfo);
            model.addAttribute("exams", examService.getAllExams());
            model.addAttribute(("keyword"), keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "teacher";
    }

    @GetMapping("/teacher/home/download/students.txt")
    public ResponseEntity<?> downloadCoursesTxt() throws Exception {
        return studentService.downloadTxtFile();
    }

    @GetMapping("/teacher/home/download/students.xml")
    public ResponseEntity<?> downloadCoursesXml() throws Exception {
        return studentService.downloadXmlFile();
    }
}
