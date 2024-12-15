package com.example.onlinecourses.util;

public class VigenereCipher {

    // Metoda szyfrowania tekstu
    public static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + (key.charAt(keyIndex % key.length()) - 'A')) % 26 + base);
                keyIndex++;
            }
            result.append(c);
        }
        return result.toString();
    }

    // Metoda deszyfrowania tekstu
    public static String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base - (key.charAt(keyIndex % key.length()) - 'A') + 26) % 26 + base);
                keyIndex++;
            }
            result.append(c);
        }
        return result.toString();
    }
}
