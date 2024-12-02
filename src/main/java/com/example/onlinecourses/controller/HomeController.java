package com.example.onlinecourses.controller;

import com.example.onlinecourses.model.*;

import com.example.onlinecourses.service.CourseService;
import com.example.onlinecourses.service.InitializeService;
import com.example.onlinecourses.service.EnrollmentService;

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
public class HomeController {
    private static List<Course> courses;
    private static List<Student> students;

    private final InitializeService initializeService;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final StudentService studentService;

    public HomeController(InitializeService initializeService, EnrollmentService enrollmentService, CourseService courseService, StudentService studentService) {
        this.initializeService = initializeService;
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping("/index")
    public String home(Model model) throws Exception {
        courses = initializeService.initializeCourses();
        students = initializeService.initializeStudents();
        List<Enrollment> enrollments = initializeService.createEnrollments(students, courses);


        // !Students part
        // Filter students by name containing 'a'
        List<Student> streamStudents = students.stream()
                .filter(student -> student.getName().contains("a"))
                .collect(Collectors.toList());

        // Calculate the number of students with the same name
        Map<String, Long> nameCount = students.stream()
                .collect(Collectors.groupingBy(Student::getName, Collectors.counting()));

        // Only duplicate names
        Map<String, Long> duplicateNames = nameCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        long amountDuplicateNames = duplicateNames.size();

        // Only names stream from another stream with map
        Stream<String> streamStudent = students.stream().map(Student::getName);

        studentService.writeStudentsToFile(students); // Write to file
        List<Student> studentsFromFile = studentService.readStudentsFromFile(); // Read from file

        // *LAB 2
        studentService.saveStudentsToXML(students); // Save students to XML

        List<String> studentStrings = studentService.readStudentsFromXMLToStrings(); // Read from XML to string
        List<Student> studentObjects = studentService.readStudentsFromXMLToObjects(); // Read from XML to objects


        //!Courses part
        // Filter courses with Java in the title
        Stream<Course> streamCoursesWithJava = courses.stream()
                .filter(course -> course.getTitle().contains("Java"));

        // Calculate the number of courses with the same duration
        Map<Integer, Long> courseCount = courses.stream()
                .collect(Collectors.groupingBy(Course::getDuration, Collectors.counting()));

        // Only duplicate durations
        Map<Integer, Long> duplicateDurationsOfCourses = courseCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        long amountOfDuplicateCourses = duplicateDurationsOfCourses.size();

        // Time of courses in minutes
        Stream<String> streamCourseDurationsInMinutes = courses.stream()
                .map(course -> course.getTitle() + " - " + (course.getDuration() * 60) + " minutes");

        // *Using Java NIO
        courseService.writeCoursesToFile(courses); // Write to file JavaNIO
        List<Course> coursesFromFile = courseService.readCoursesFromFile(); // Read from file JavaNIO

        // *Using Java IO
        courseService.writeCoursesToFileJavaIO(courses); // Write to file JavaIO
        List<Course> coursesFromFileJavaIO = courseService.readCoursesFromFileJavaIO(); // Read from file JavaIO


        // *LAB 2
        courseService.saveToXMLCourses(courses); // Write to XML

        List<String> readedCourses = courseService.readCourseFromXML(); // Read from XML to string
        List<Course> loadedCourses = courseService.CoursesFromXMLtoObjects(); // Read from XML to objects


        // !Enrollments part
        // Filter enrollments with student name containing 'Shepeta'
        Stream<Enrollment> streamEnrollmentsContainsSth = enrollments.stream()
                .filter(enrollment -> enrollment.getStudent().getName().contains("Shepeta"));

        // Count enrollments for each student
        Map<String, Long> studentEnrollmentCounts = enrollments.stream()
                .collect(Collectors.groupingBy(
                        enrollment -> enrollment.getStudent().getName(), // Group by student name
                        Collectors.counting() // Count occurrences
                ));

        // Create a Stream from another stream with map
        Stream<String> streamWithMap = enrollments.stream().map(enrollment -> enrollment.getStudent().getName() + " enrolled in " + enrollment.getCourse().getTitle());

        enrollmentService.writeEnrollmentsToFile(enrollments); // Write to file
        List<Enrollment> enrollmentsFromFile = enrollmentService.readEnrollmentsFromFile(); // Read from file

        // *LAB 2
        enrollmentService.saveEnrollmentsToXML(enrollments); // Save enrollments to XML
        List<Enrollment> enrollmentsFromXML = enrollmentService.readEnrollmentsFromXML(); // Read enrollments from XML


        // !LAB 3 all classes
        // * Teacher, StudentClass, Assignment
        Teacher originalTeacher = new Teacher("Jan Kowalski", 40, 15, 50.0, 101);
        StudentClass studentClass = new StudentClass(students, courses.get(0), originalTeacher);

        Assignment assignment = Assignment.builder()
                .id(1)
                .title("Zadanie 1")
                .description("Utwórz 3 klasy encyjne z wykorzystaniem adnotacji Lombok posiadające publiczne metody dostępowe, pola wymagane, konstruktory argumentow")
                .assignedBy(originalTeacher)
                .isCompleted(false)
                .build();


        Teacher teacherCopy = new Teacher(originalTeacher);
        originalTeacher.setName("Jan Nowak");
        originalTeacher.setAge(41);
        Teacher teacherSaved = new Teacher(originalTeacher);
        teacherCopy.restoreState(originalTeacher);


        // * Certificate, Feedback, Schedule
        Certificate certificate = new Certificate(1, "java", "28/10/2024", 4, "MasterDegree");

        Certificate certificateCopy = new Certificate(certificate);//tworzymy kopie
        certificate.setCourseTitle("Advanced java");//zmieniamy startowa instancje certyfikatu
        certificate.setGrade(5);
        Certificate certificateSave = new Certificate(certificate);//nowa instancja ze zmiana danych
        certificateCopy.resetStateCertificate(certificate);

        Feedback feedback = Feedback.builder()
                .CourseTitle("Advanced java")
                .score(4)
                .comment("Bardzo dobry kurs")
                .date("26/10/2024")
                .build();

        Schedule schedule = new Schedule("Advanced java", "15/04/2024", "15/10/2024", "Lecture hall 201");


        // * Exam, ExamSession, ExamDTO
        Exam exam = new Exam("Java", LocalDate.of(2024, 12, 16));
        exam.setMaxScore(100);

        ExamSession examSession = ExamSession.builder()
                .id(1L)
                .startDate(LocalDate.of(2024, 12, 10))
                .endDate(LocalDate.of(2024, 12, 30))
                .description("Winter exam session")
                .build();


        ExamDTO examDTO = new ExamDTO(exam);

        examDTO.update("Git", LocalDate.of(2024, 12, 15), 85);

        // Create a new variable to hold the updated values
        ExamDTO updatedExamDTO = new ExamDTO(examDTO); // Create a copy of the updated ExamDTO
        model.addAttribute("updatedExamDTO", updatedExamDTO);

        // Rollback to the original values
        examDTO.rollback();
        model.addAttribute("rolledBackExamDTO", examDTO);


        try {
            // !LAB 1-2
            model.addAttribute("courses", courses);
            model.addAttribute("streamCoursesWithJava", streamCoursesWithJava);
            model.addAttribute("duplicateDurationsOfCourses", duplicateDurationsOfCourses);
            model.addAttribute("amountOfDuplicateCourses", amountOfDuplicateCourses);
            model.addAttribute("streamCourseDurationsInMinutes", streamCourseDurationsInMinutes);
            model.addAttribute("coursesFromFile", coursesFromFile);
            model.addAttribute("coursesFromFileJavaIO", coursesFromFileJavaIO);
            model.addAttribute("StringCoursesFromXML", readedCourses);
            model.addAttribute("ObjectsCoursesFromXML", loadedCourses);


            model.addAttribute("students", students);
            model.addAttribute("filteredStudents", streamStudents);
            model.addAttribute("duplicateNames", duplicateNames);
            model.addAttribute("amountDuplicateNames", amountDuplicateNames);
            model.addAttribute("studentsFromFile", studentsFromFile);
            model.addAttribute("studentStrings", studentStrings);
            model.addAttribute("studentObjects", studentObjects);


            model.addAttribute("enrollments", enrollments);
            model.addAttribute("streamEnrollmentsContainsSth", streamEnrollmentsContainsSth);
            model.addAttribute("studentEnrollmentCounts", studentEnrollmentCounts);
            model.addAttribute("enrollmentsFromFile", enrollmentsFromFile);
            model.addAttribute("enrollmentsFromXML", enrollmentsFromXML);

            // !LAB 3
            model.addAttribute("teacher", originalTeacher);
            model.addAttribute("teacherSaved", teacherSaved);
            model.addAttribute("studentClass", studentClass);
            model.addAttribute("assignment", assignment);

            model.addAttribute("certificate", certificate);
            model.addAttribute("certificateSave", certificateSave);
            model.addAttribute("feedback", feedback);
            model.addAttribute("schedule", schedule);

            model.addAttribute("exam", exam);
            model.addAttribute("examSession", examSession);
            // model attributes for ExamDTO are added above


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }

    public static Student findStudentByName(String name) {
        return students.stream()
                .filter(student -> student.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static Course findCourseByTitle(String title) {
        return courses.stream()
                .filter(course -> course.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

}
