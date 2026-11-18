package main.java;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Translator {

    private final Map<String, String> dictionary;

    public Translator() {
        this.dictionary = new HashMap<>();
    }

    public void addWord(String en, String ua) {
        if (en == null || ua == null) {
            throw new IllegalArgumentException("Слово не може бути null!");
        }

        en = en.toLowerCase().trim();
        ua = ua.toLowerCase().trim();

        if (en.isEmpty() || ua.isEmpty()) {
            throw new IllegalArgumentException("Слово не може бути порожнім!");
        }
        if (dictionary.containsKey(en)) {
            throw new IllegalArgumentException("Слово \"" + en + "\" вже є у словнику!");
        }

        dictionary.put(en, ua);
    }

    public String translate(String phrase) {
        StringJoiner result = new StringJoiner(" ");

        for (String word : phrase.toLowerCase().trim().split("\\s+")) {
            // залишаємо літери, цифри та апостроф
            String clean = word.replaceAll("[^\\p{L}\\p{N}']", "");
            if (clean.isEmpty()) {
                continue;
            }
            result.add(dictionary.getOrDefault(clean, clean));
        }
        return result.toString();
    }

    public int size() {
        return dictionary.size();
    }
}