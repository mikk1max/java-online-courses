package com.example.onlinecourses.service;

import java.util.List;

public interface FileWriterService<T> {
    void saveToFile(List<T> items);
    void saveToXML(List<T> items);
}
