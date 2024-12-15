package com.example.onlinecourses.communication;

import com.example.onlinecourses.util.VigenereCipher;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Client {
    private JTextArea answerArea;
    private JTextField requestField;

    private final String encryptionKey = "MYSECRETKEY";

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        JFrame frame = new JFrame("Klient do obsługi plików");
        JPanel panel = new JPanel();
        answerArea = new JTextArea(15, 50);
        answerArea.setLineWrap(true);
        answerArea.setWrapStyleWord(true);
        answerArea.setEditable(false);
        JScrollPane scrolling = new JScrollPane(answerArea);
        scrolling.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrolling.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        requestField = new JTextField(20);
        JButton getListButton = new JButton("Pobierz listę plików");
        JButton getFileContentButton = new JButton("Pobierz plik");

        getListButton.addActionListener(e -> getFilesList());
        getFileContentButton.addActionListener(e -> getFileContent(requestField.getText()));

        panel.add(scrolling);
        panel.add(requestField);
        panel.add(getListButton);
        panel.add(getFileContentButton);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(new Dimension(600, 400));
        frame.setVisible(true);
    }

    private void getFilesList() {
        try {
            URL url = new URL("http://127.0.0.1:8080/api/files");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            List<String> files = objectMapper.readValue(response.toString(), List.class);

            answerArea.setText("Dostępne pliki:\n");
            for (String plik : files) {
                answerArea.append(plik + "\n");
            }

        } catch (IOException e) {
            answerArea.append("Błąd podczas pobierania listy plików: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void getFileContent(String fileName) {
        try {
            String encryptedFileName = VigenereCipher.encrypt(fileName, encryptionKey);

            URL url = new URL("http://127.0.0.1:8080/api/files/" + encryptedFileName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 404) {
                answerArea.append("Plik " + fileName + " nie istnieje!\n");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            String decryptedContent = VigenereCipher.decrypt(content.toString(), encryptionKey);

            answerArea.append("Zawartość pliku " + fileName + ":\n");
            answerArea.append(decryptedContent + "\n");

        } catch (IOException e) {
            answerArea.append("Błąd podczas pobierania pliku: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
}
