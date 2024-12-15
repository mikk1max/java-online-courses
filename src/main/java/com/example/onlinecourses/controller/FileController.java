package com.example.onlinecourses.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final String dataDir = "data";


    @GetMapping
    public ResponseEntity<List<String>> listFiles() {
        try {
            // Получаем путь к папке
            ClassPathResource resource = new ClassPathResource(dataDir);
            Path folderPath = resource.getFile().toPath();

            // Список файлов
            List<String> files = Files.list(folderPath)
                                      .map(path -> path.getFileName().toString())
                                      .collect(Collectors.toList());
            return ResponseEntity.ok(files);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/{filename}")
    public ResponseEntity<String> getFile(@PathVariable String filename) {
        try {

            ClassPathResource resource = new ClassPathResource(dataDir + "/" + filename);
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }


            String content = StreamUtils.copyToString(resource.getInputStream(), java.nio.charset.StandardCharsets.UTF_8);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
            return ResponseEntity.status(500).body("Błąd podczas czytania pliku: " + e.getMessage());
        }
    }
}
