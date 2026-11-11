package lab5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class WordCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до файлу: ");
        String filePath = scanner.nextLine();

        String maxLine = "";
        int maxWordCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // Розбиваємо рядок за пробілами та розділовими знаками
                String[] words = currentLine.trim().split("\\s+");
                int wordCount = (currentLine.trim().isEmpty()) ? 0 : words.length;

                if (wordCount > maxWordCount) {
                    maxWordCount = wordCount;
                    maxLine = currentLine;
                }
            }

            if (maxWordCount > 0) {
                System.out.println("Рядок з максимальною кількістю слів (" + maxWordCount + " слів):");
                System.out.println(maxLine);
            } else {
                System.out.println("Файл порожній або не містить слів.");
            }

        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }
}
