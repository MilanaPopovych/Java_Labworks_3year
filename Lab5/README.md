# Лабораторна робота № 5 ІО-46 Попович Мілана
## Тема: Потоки вводу/виводу та серіалізація
---

## Завдання
1. Напишіть програму, яка отримає рядок з максимальною кількістю слів із заданого файлу.
2. Відкрийте код додатка за завданням `Task 3 Simple OOP` і внесіть наступні зміни:
- додайте в меню команду для збереження набору об'єктів в файл;
- передбачте можливість читання набору об'єктів з файлу.
3. Зашифрувати вхідний символьний потік за наступним алгоритмом: замінити кожний символ на такий, код якого більший на значення
коду ключового символу. При виконанні завдання: \
a. Реалізувати метод, який реалізує шифрування \
b. Реалізувати метод, який реалізує дешифрування. \
c. Використати для розв’язання задачі потоки типу
FilerOutputStream-FilterInputStream/FilterWriter-FilteredReader.
4. Підрахувати частоту, з якою зустрічаються теги на сторінці по URL
(URL на ваш вибір). \
a. Вивести результати наступним чином: в порядку зростання 
для тегів в лексикографічному порядку \
b. Вивести результати наступним чином: в порядку зростання по
частоті появи тегів

ВИМОГИ
1. Передбачте можливість введення розташування та ім’я файлу для
збереження даних.
2. Використовуйте об'єктні потоки для роботи з файлом.
3. Застосуйте серіалізацію за замовчуванням для роботи з файлами.
4. Виділіть роботу з файлами в окремий клас.
5. Дані для пошуку інформації в наборі даних повинні вводитися
набором з клавіатури.
6. Передбачте обробку коректності введення даних через блоки try-
catch.
---
## Виконання завдання
*Опис програми:*
1. Програма пошуку максимальної кількості слів (`WordCounter`)
2. Система управління фігурами (Task 3 Simple OOP) зі збереженням та зчитуванням набору об'єктів з файлу
3. Виконуваний файл з методами шифрування та дешифрування тексту (`EncryptionApp`)
4. Програма для аналізу HTML-тегів (`TagCounter`)

**Лістинг коду**

### Файл WordCounter.java:
```java
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
                // розбиття рядка
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
```

*Результати тестування програми:*

<img src="" width="">

### Task 3 Simple OOP 
До проєкту було додано пошук фігур за мінімальною площею та обробку винятків (try-catch) для захисту від некоректного вводу та FileManager для їх серіалізації/десеріалізації у бінарний файл.

*Результати тестування програми:*

<img src="" width="">

### Файл EncryptionApp.java

```java
package lab5;

import lab5.file.*;
import java.io.*;

public class EncryptionApp {
    public static void main(String[] args) {
        String textToEncrypt = "Hello, Java World! Привіт Світ!";
        char key = 'K';
        String fileName = "encrypted.txt";
        // шифрування та запис у файл
        try (Writer fileWriter = new FileWriter(fileName);
             EncryptWriter encryptWriter = new EncryptWriter(fileWriter, key)) {

            encryptWriter.write(textToEncrypt);
            System.out.println("Текст успішно зашифровано і записано.");

        } catch (IOException e) {
            System.err.println("Помилка запису: " + e.getMessage());
        }
        // дешифрування та читання з файлу
        try (Reader fileReader = new FileReader(fileName);
             DecryptReader decryptReader = new DecryptReader(fileReader, key)) {

            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = decryptReader.read()) != -1) {
                sb.append((char) c);
            }
            System.out.println("Дешифрований текст з файлу:");
            System.out.println(sb.toString());

        } catch (IOException e) {
            System.err.println("Помилка читання: " + e.getMessage());
        }
    }
}
```
*Результати тестування програми:*

<img src="" width="">

### Файл TagCounter.java

```java
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
```

*Результати тестування програми:*

<img src="" width="">

---
## Висновки
У результаті виконання лабораторної роботи було розроблено програмні додатки мовою Java, що закріплюють принципи ООП, роботу з потоками вводу/виводу, колекціями та мережею.
