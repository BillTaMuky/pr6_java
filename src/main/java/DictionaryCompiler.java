package main.java;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DictionaryCompiler {
    private static final String FILE_NAME = "dictionary.txt";
    private final Translator translator = new Translator();

    public DictionaryCompiler() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            loadWordsFromFile(FILE_NAME);
        } else {
            System.out.println("Файл " + FILE_NAME + " не знайдено, словник порожній.");
        }
    }

    // якщо слово вже є, то транслятор кине виняток і у файл нічого не запишеться
    public void addWord(String en, String ua) {
        translator.addWord(en, ua);
        saveWordToFile(en, ua);
    }

    private void saveWordToFile(String en, String ua) {
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(FILE_NAME, true), StandardCharsets.UTF_8)) {
            writer.write(en.toLowerCase().trim() + "=" + ua.toLowerCase().trim()
                    + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Помилка запису слова в файл!", e);
        }
    }

    public void loadWordsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.trim().split("=", 2);
                if (parts.length != 2) {
                    System.err.println("Рядок " + lineNumber + " пропущено: немає знака '='");
                    continue;
                }
                try {
                    translator.addWord(parts[0], parts[1]);
                } catch (IllegalArgumentException e) {
                    System.err.println("Рядок " + lineNumber + " пропущено: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Помилка читання файлу!", e);
        }
    }

    public Translator getTranslator() {
        return translator;
    }
}