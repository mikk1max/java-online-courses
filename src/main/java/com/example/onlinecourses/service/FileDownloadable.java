package com.example.onlinecourses.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface FileDownloadable {

    ResponseEntity<InputStreamResource> downloadTxtFile() throws IOException;

    ResponseEntity<InputStreamResource> downloadXmlFile() throws IOException;
}
