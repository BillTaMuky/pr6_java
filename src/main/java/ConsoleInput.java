package main.java;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);
    private final DictionaryCompiler compiler = new DictionaryCompiler();
    private final Translator translator = compiler.getTranslator();

    public void run() {
        while (true) {
            printMenu();
            String choice;
            try {
                choice = ask("Ваш вибір: ");
            } catch (NoSuchElementException e) {
                return;
            }

            try {
                switch (choice) {
                    case "1" -> showTranslation();
                    case "2" -> addWord();
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Невідомий пункт меню.");
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\nМеню");
        System.out.println("1 - Перекласти фразу");
        System.out.println("2 - Додати слово до словника");
        System.out.println("0 - Вихід");
    }

    private void showTranslation() {
        String line = ask("Введіть фразу англійською: ");
        if (line.isEmpty()) {
            System.out.println("Порожня фраза!");
            return;
        }
        System.out.println("Переклад: " + translator.translate(line));
    }

    private void addWord() {
        String en = ask("Введіть слово англійською: ");
        String ua = ask("Введіть слово українською: ");
        if (en.isEmpty() || ua.isEmpty()) {
            System.out.println("Слово не може бути порожнім!");
            return;
        }
        compiler.addWord(en, ua);
        System.out.println("Слово додано.");
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}