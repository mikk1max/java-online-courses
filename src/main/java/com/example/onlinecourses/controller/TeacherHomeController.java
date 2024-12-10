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
    private static List<Student> students;
    private static List<Exam> exams;
    private static List<Map.Entry<String, String>> enrollmentInfo;
    private static List<Student> filtered_students;

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
        //Students
        students = studentService.getAllStudents();

        // Write to file
        studentService.writeStudentsToFile(students);
        studentService.saveStudentsToXML(students);

        //filter students
        filtered_students = studentService.filterStudents(students, keyword);

        //Enrollments
        enrollmentInfo = enrollmentService.getEnrollmentsInfo(enrollmentService.getAllEnrollments());

        //Exams
        exams = examService.getAllExams();

        try {
            model.addAttribute("students", students);
            model.addAttribute("filtered_students", filtered_students);
            model.addAttribute("enrollmentInfo", enrollmentInfo);
            model.addAttribute("exams", exams);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "teacher";
    }

    @GetMapping("/download/students.txt")
    public ResponseEntity downloadCoursesTxt() throws Exception {
        return studentService.downloadCoursesTxtFile();
    }

    @GetMapping("/download/students.xml")
    public ResponseEntity downloadCoursesXml() throws Exception {
        return studentService.downloadCoursesXmlFile();
    }
}
