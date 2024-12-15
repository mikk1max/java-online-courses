package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Student;
import com.example.onlinecourses.repository.StudentRepository;
import org.springframework.core.io.InputStreamResource;
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
public class StudentService implements FileWriterService<Student>, FileDownloadable{

    private static final String STUDENT_FILE_PATH = "./src/main/resources/data/students.txt";
    private static final String STUDENT_FILE_XML_PATH = "./src/main/resources/data/students.xml";

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadTxtFile() throws IOException {
        return DownloadService.downloadFile(STUDENT_FILE_PATH, MediaType.TEXT_PLAIN);
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadXmlFile() throws IOException {
        return DownloadService.downloadFile(STUDENT_FILE_XML_PATH, MediaType.APPLICATION_XML);
    }

    @Override
    public void saveToFile(List<Student> students) {
        List<String> studentLines = students.stream()
                .sorted((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()))
                .map(student -> student.getName() + "," + student.getAge())
                .collect(Collectors.toList());
        try {
            Files.write(Paths.get(STUDENT_FILE_PATH), studentLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToXML(List<Student> students) {
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

                xmlStreamWriter.writeEndElement();
            }

            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();

        } catch (IOException | XMLStreamException e) {
            System.err.println("Error while saving students to XML: " + e.getMessage());
        }
    }

    public List<Student> filterStudents(List<Student> students, String keyword) {
        return students.stream()
                .filter(student -> student.getName().toLowerCase().contains(keyword.toLowerCase())).toList();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAllByIsActiveTrue();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student with ID " + id + " not found"));
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
        }).orElseThrow(() -> new IllegalArgumentException("Student with ID " + id + " not found"));
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