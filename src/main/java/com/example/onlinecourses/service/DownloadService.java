package com.example.onlinecourses.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class DownloadService {

    public static ResponseEntity<InputStreamResource> downloadFile(String filePath, MediaType mediaType) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("File not found at path: " + filePath);
        }

        InputStream inputStream = new FileInputStream(file);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .body(resource);
    }
}
