package com.example.onlinecourses.service;

import com.example.onlinecourses.model.Course;
import com.example.onlinecourses.repository.CourseRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.stream.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService implements FileWriterService<Course>, FileDownloadable {
    private static final String COURSES_FILE_PATH = "./src/main/resources/data/courses.txt";
    private static final String XML_COURSES_FILE_PATH = "./src/main/resources/data/courses.xml";

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadTxtFile() throws IOException {
        return DownloadService.downloadFile(COURSES_FILE_PATH, MediaType.TEXT_PLAIN);
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadXmlFile() throws IOException {
        return DownloadService.downloadFile(XML_COURSES_FILE_PATH, MediaType.APPLICATION_XML);
    }

    @Override
    public void saveToFile(List<Course> courses) {
        List<String> coursesLines = courses.stream()
                .sorted(Comparator.comparingInt(Course::getDuration))
                .map(course -> course.getTitle() + "-" + course.getDuration())
                .collect(Collectors.toList());
        try {
            Files.write(Paths.get(COURSES_FILE_PATH), coursesLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveToXML(List<Course> courses) {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        try (FileOutputStream outputStream = new FileOutputStream(XML_COURSES_FILE_PATH)) {
            XMLStreamWriter xmlStreamWriter = outputFactory.createXMLStreamWriter(outputStream);
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeStartElement("courses");

            for (Course course : courses) {
                xmlStreamWriter.writeStartElement("course");

                xmlStreamWriter.writeStartElement("title");
                xmlStreamWriter.writeCharacters(course.getTitle());
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeStartElement("description");
                xmlStreamWriter.writeCharacters(course.getDescription());
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeStartElement("duration");
                xmlStreamWriter.writeCharacters(String.valueOf(course.getDuration()));
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeEndElement();
            }

            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
        } catch (IOException | XMLStreamException e) {
            System.err.println("Error while saving courses to XML: " + e.getMessage());
        }
    }

    public List<Course> getFilterCourses(List<Course> courses, String keyword) {
        return courses.stream()
                .filter(course -> course.getTitle().toLowerCase().contains(keyword.toLowerCase())).toList();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAllByIsActiveTrue();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course with ID " + id + " not found"));
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id).map(existingCourse -> {
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setDescription(updatedCourse.getDescription());
            if (updatedCourse.getDuration() > 0) {
                existingCourse.setDuration(updatedCourse.getDuration());
            }
            existingCourse.setIsActive(updatedCourse.getIsActive());
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