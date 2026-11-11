package lab5;

import java.net.URI;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть URL: ");
        String urlString = scanner.nextLine();

        Map<String, Integer> tagMap = new HashMap<>();

        try {
            URL url = URI.create(urlString).toURL();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                Pattern pattern = Pattern.compile("<\\s*([a-zA-Z0-9]+)");

                while ((line = reader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String tagName = matcher.group(1).toLowerCase();
                        tagMap.put(tagName, tagMap.getOrDefault(tagName, 0) + 1);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Помилка під час зчитування URL: " + e.getMessage());
            return;
        }
        if (tagMap.isEmpty()) {
            System.out.println("Теги не знайдено або сторінка порожня.");
            return;
        }
        // a. Вивід в порядку зростання для тегів в лексикографічному порядку
        System.out.println("\n****** a. Теги в лексикографічному порядку ******");
        var lexList = new ArrayList<>(tagMap.entrySet());
        lexList.sort(Map.Entry.comparingByKey());
        for (var entry : lexList) {
            System.out.printf("<%-10s> : %d%n", entry.getKey(), entry.getValue());
        }
        // b. Вивід в порядку зростання по частоті появи тегів
        System.out.println("\n****** b. Теги в порядку зростання частоти ******");
        var freqList = new ArrayList<>(tagMap.entrySet());
        freqList.sort(Map.Entry.comparingByValue());
        for (var entry : freqList) {
            System.out.printf("<%-10s> : %d%n", entry.getKey(), entry.getValue());
        }
    }
}