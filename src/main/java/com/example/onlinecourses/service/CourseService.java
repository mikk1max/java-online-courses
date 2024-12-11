package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Course;
import com.example.onlinecourses.model.User;
import com.example.onlinecourses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.stream.*;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseService {

    private static final String COURSES_FILE_PATH = "./src/main/resources/data/courses.txt";
    private static final String XML_COURSES_FILE_PATH = "./src/main/resources/data/courses.xml";
    @Autowired
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public ResponseEntity<InputStreamResource> downloadCoursesTxtFile() throws IOException {
        File file = new File(COURSES_FILE_PATH);
        InputStream inputStream = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }

    public ResponseEntity<InputStreamResource> downloadCoursesXmlFile() throws IOException {
        File file = new File(XML_COURSES_FILE_PATH);
        InputStream inputStream = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_XML)
                .body(resource);
    }

    public void writeCoursesToFile(List<Course> courses) {
        List<String> coursesLines = courses.stream()
                .sorted(Comparator.comparingInt(Course::getDuration)) // Sort courses by duration
                .map(course -> course.getTitle() + "-" + course.getDuration()) // Map each course to a string
                .collect(Collectors.toList());
        try {
            Files.write(Paths.get(COURSES_FILE_PATH), coursesLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToXMLCourses(List<Course> courses) {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        try (FileOutputStream outputStream = new FileOutputStream(XML_COURSES_FILE_PATH)) {
            XMLStreamWriter xmlStreamWriter = outputFactory.createXMLStreamWriter(outputStream);
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeStartElement("courses"); // Start main element

            for (Course course : courses) {
                xmlStreamWriter.writeStartElement("course"); // Start element "course"

                xmlStreamWriter.writeStartElement("title");
                xmlStreamWriter.writeCharacters(course.getTitle()); // Course title
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeStartElement("description");
                xmlStreamWriter.writeCharacters(course.getDescription()); // Course description
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeStartElement("duration");
                xmlStreamWriter.writeCharacters(String.valueOf(course.getDuration())); // Course duration
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeEndElement(); // End element "course"
            }

            xmlStreamWriter.writeEndElement(); // End main element
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
        } catch (IOException | XMLStreamException e) {
            System.err.println("Error while saving courses to XML: " + e.getMessage());
        }
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAllByIsActiveTrue();
    }

    public List<Course> filterCourses(List<Course> courses, String keyword) {
        List<Course> filtered_courses = courses.stream()
                .filter(course -> course.getTitle().toLowerCase().contains(keyword.toLowerCase())).toList();
        return filtered_courses;
    }


    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id).map(existingCourse -> {
            if (updatedCourse.getTitle() != null) {
                existingCourse.setTitle(updatedCourse.getTitle());
            }
            if (updatedCourse.getDescription() != null) {
                existingCourse.setDescription(updatedCourse.getDescription());
            }
            if (updatedCourse.getDuration() != 0) {
                existingCourse.setDuration(updatedCourse.getDuration());
            }
            if (updatedCourse.getIsActive() != null) {
                existingCourse.setIsActive(updatedCourse.getIsActive());
            }
            return courseRepository.save(existingCourse);
        }).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public void deactivateCourse(Long id) {
        courseRepository.findById(id).ifPresent(course -> {
            course.setIsActive(false);
            courseRepository.save(course);
        });
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}