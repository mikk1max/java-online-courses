package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Student;
import com.example.onlinecourses.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.stream.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final String STUDENT_FILE_PATH = "./src/main/resources/data/students.txt";
    private static final String STUDENT_FILE_XML_PATH = "./src/main/resources/data/students.xml";

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<InputStreamResource> downloadCoursesTxtFile() throws IOException {
        File file = new File(STUDENT_FILE_PATH);
        InputStream inputStream = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }

    public ResponseEntity<InputStreamResource> downloadCoursesXmlFile() throws IOException {
        File file = new File(STUDENT_FILE_XML_PATH);
        InputStream inputStream = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_XML)
                .body(resource);
    }

    public void writeStudentsToFile(List<Student> students) {
        List<String> studentLines = students.stream()
                .sorted((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName())) // Sort students by name
                .map(student -> student.getName() + "," + student.getAge()) // Map each student to a string
                .collect(Collectors.toList()); // Collect all strings into a list
        try {
            Files.write(Paths.get(STUDENT_FILE_PATH), studentLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveStudentsToXML(List<Student> students) {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        try (FileOutputStream outputStream = new FileOutputStream(STUDENT_FILE_XML_PATH)) {
            XMLStreamWriter xmlStreamWriter = outputFactory.createXMLStreamWriter(outputStream);
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeStartElement("students");

            for (Student student : students) {
                xmlStreamWriter.writeStartElement("student");

                xmlStreamWriter.writeStartElement("name");
                xmlStreamWriter.writeCharacters(student.getName());
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeStartElement("age");
                xmlStreamWriter.writeCharacters(String.valueOf(student.getAge()));
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeEndElement(); // End student
            }

            xmlStreamWriter.writeEndElement(); // End students
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();

        } catch (IOException | XMLStreamException e) {
            System.err.println("Error while saving students to XML: " + e.getMessage());
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAllByIsActiveTrue();
    }

    public List<Student> filterStudents(List<Student> students, String keyword) {
        List<Student> filtered_students = students.stream()
                .filter(student -> student.getName().toLowerCase().contains(keyword.toLowerCase())).toList();
        return filtered_students;
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(existingStudent -> {

            existingStudent.setName(updatedStudent.getName());
            existingStudent.setAge(updatedStudent.getAge());
            existingStudent.setIsActive(updatedStudent.getIsActive());
            return studentRepository.save(existingStudent);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public void deactivateStudent(Long id) {
        studentRepository.findById(id).ifPresent(student -> {
            student.setIsActive(false);
            studentRepository.save(student);
        });
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}