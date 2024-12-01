package com.example.onlinecourses.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    // Funkcja do haszowania hasła
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Funkcja do porównania hasła z zapisanym hashem
    public static boolean checkPassword(String password, String storedHash) {
        // Sprawdzamy, czy hasło po haszowaniu odpowiada zapisanej wartości
        return storedHash.equals(hashPassword(password));
    }
}
