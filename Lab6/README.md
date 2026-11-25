# Лабораторна робота № 6 ІО-46 Попович Мілана
## Тема: Java Collections Framework
---

## Завдання
Напишіть консольний додаток, який:
- описує клас, який є перекладачем з англійської мови та містить словник англійських та українських слів у вигляді колекції `HashMap<String, String>`, метод для додавання пар слів та метод для перекладу отриманої фрази;
- створює екземпляр описаного класу, наповнює словник (також підтримує введення даних з клавіатури);
- вводить деяку фразу англійською мовою та відображає її переклад українською мовою.

---
## Виконання завдання
*Опис програми:*
1. Клас `Translator`: реалізує словник на основі колекції `HashMap<String, String>`, перевіряє коректність введених слів за допомогою регулярних виразів, 
відстежує статус додавання (`ADDED`, `UPDATED`, `DUPLICATE`) та виконує пофразовий переклад зі збереженням регістру і розділових знаків.
2. Клас `Main`: виконує початкове наповнення словника, забезпечує інтерактивне додавання нових пар слів із клавіатури 
з обробкою винятків (`try-catch`) та перекладає введену користувачем фразу.
3. Клас `TranslatorTest`: набір модульних тестів (JUnit 5) для перевірки логіки перекладу, регістру, 
пунктуації, статусів додавання та валідації помилкового введення.

## **Лістинг коду**

### Файл Translator.java:
```java
package lab6;

import java.util.HashMap;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class Translator {
    // перевірка на цілісність слова (англ)
    private static final Pattern ENGLISH_WORD_PATTERN =
            Pattern.compile("^[a-zA-Z]+(?:['’-][a-zA-Z]+)*$");
    // перевірка на цілісність слова (укр)
    // виключення, якщо англ. слово потребує 2-3 укр. слова
    private static final Pattern UKRAINIAN_TRANSLATION_PATTERN =
            Pattern.compile("^[а-щьюяґєіїА-ЩЬЮЯҐЄІЇ]+(?:['’ʼ-][а-щьюяґєіїА-ЩЬЮЯҐЄІЇ]+)*(?:\\s+[а-щьюяґєіїА-ЩЬЮЯҐЄІЇ]+(?:['’ʼ-][а-щьюяґєіїА-ЩЬЮЯҐЄІЇ]+)*){0,2}$");
    private final HashMap<String, String> dictionary;

    // статуси перевірки слів
    public enum AddStatus {
        ADDED,          // нове слово
        UPDATED,        // оновлено переклад для наявного слова
        DUPLICATE       // пара "слово -> переклад" вже існує
    }

    public Translator() {
        this.dictionary = new HashMap<>();
    }

    public AddStatus addWord(String englishWord, String ukrainianWord) {
        if (englishWord == null || ukrainianWord == null
                || englishWord.isBlank() || ukrainianWord.isBlank()) {
            throw new IllegalArgumentException("Слова не можуть бути порожніми.");
        }
        String cleanEng = englishWord.trim();
        String cleanUkr = ukrainianWord.trim();

        // перевірка вводу на к-сть слів
        if (!ENGLISH_WORD_PATTERN.matcher(cleanEng).matches()) {
            throw new IllegalArgumentException(
                    "Англійське слово має бути одним, без пробілів, цифр та розділових знаків.");
        }
        if (!UKRAINIAN_TRANSLATION_PATTERN.matcher(cleanUkr).matches()) {
            throw new IllegalArgumentException(
                    "Український переклад має бути одним словом кирилицею без пробілів та розділових знаків.");
        }

        String key = cleanEng.toLowerCase();
        String existingTranslation = dictionary.get(key);

        // додавання нового слова
        if (existingTranslation == null) {
            dictionary.put(key, cleanUkr);
            return AddStatus.ADDED;
        }
        if (existingTranslation.equals(cleanUkr)) {
            return AddStatus.DUPLICATE;
        }
        dictionary.put(key, cleanUkr);
        return AddStatus.UPDATED;
    }

    public String translatePhrase(String phrase) {
        if (phrase == null || phrase.isBlank()) {
            return "";      // перевірка на порожні дані
        }
        // розбиття на токени
        String[] tokens = phrase.trim().split("\\s+");
        // почерговий переклад та об'єднання
        StringJoiner result = new StringJoiner(" ");
        for (String token : tokens) {
            result.add(translateToken(token));
        }
        return result.toString();
    }

    // допоміжний метод
    private String translateToken(String token) {
        int start = 0;  // початок слова
        while (start < token.length() && !Character.isLetterOrDigit(token.charAt(start))) {
            start++;
        }
        int end = token.length();   // кінець слова
        while (end > start && !Character.isLetterOrDigit(token.charAt(end - 1))) {
            end--;
        }
        if (start == end) {
            return token;
        }
        String prefix = token.substring(0, start);  // знаки перед словом
        String word = token.substring(start, end);   // слово без пунктуацій
        String suffix = token.substring(end);     // знаки після слова
        String translated = dictionary.getOrDefault(word.toLowerCase(), word);
        if (Character.isUpperCase(word.charAt(0)) && !translated.isEmpty()) {
            translated = Character.toUpperCase(translated.charAt(0)) + translated.substring(1);
        }
        return prefix + translated + suffix;
    }
}
```

### Файл Main.java:
```java
package lab6;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Translator translator = new Translator();
        // поч. наповнення словника
        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Англо-український перекладач");
            System.out.println("Додайте власні слова до словника (або натисніть Enter):");
            while (true) {
                System.out.print("Англійське слово: ");
                String engWord = scanner.nextLine().trim();
                if (engWord.isEmpty()) {
                    break;
                }
                System.out.print("Український переклад: ");
                String ukrWord = scanner.nextLine().trim();
                if (ukrWord.isEmpty()) {
                    System.out.println("Переклад не може бути порожнім.\n");
                    continue;
                }
                try {
                    Translator.AddStatus status = translator.addWord(engWord, ukrWord);
                    switch (status) {
                        case ADDED -> System.out.println("Пару (" + engWord + " -> " + ukrWord + ") додано.\n");
                        case UPDATED -> System.out.println("Переклад для слова '" + engWord + "' оновлено на '" + ukrWord + "'.\n");
                        case DUPLICATE -> System.out.println("Пара (" + engWord + " -> " + ukrWord + ") вже є у словнику.\n");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Помилка: " + e.getMessage() + "\n");
                }
            }
            System.out.print("\nВведіть фразу англійською мовою: ");
            String phrase = scanner.nextLine();
            String translatedPhrase = translator.translatePhrase(phrase);
            System.out.println("Переклад українською: " + translatedPhrase);
        }
    }
}
```

### Файл TranslatorTest.java:
```java
import lab6.Translator;
import lab6.Translator.AddStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TranslatorTest {

    private Translator translator;

    @BeforeEach
    void setUp() {
        translator = new Translator();
        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("java", "джава");
    }

    @Test
    @DisplayName("Переклад окремих слів зі словника")
    void testTranslateSingleWords() {
        assertEquals("привіт", translator.translatePhrase("hello"));
        assertEquals("світ", translator.translatePhrase("world"));
        assertEquals("джава", translator.translatePhrase("java"));
    }

    @Test
    @DisplayName("Нечутливість до регістру та відновлення великої першої літери")
    void testCaseInsensitivityAndCapitalization() {
        assertEquals("привіт", translator.translatePhrase("hello"));
        assertEquals("Привіт", translator.translatePhrase("Hello"));
        assertEquals("Привіт", translator.translatePhrase("HELLO"));
        assertEquals("Джава", translator.translatePhrase("Java"));
    }

    @Test
    @DisplayName("Переклад фрази зі збереженням розділових знаків")
    void testTranslatePhraseWithPunctuation() {
        assertEquals("Привіт, світ! Джава.", translator.translatePhrase("Hello, world! Java."));
        assertEquals("(Привіт) - \"світ\"?!", translator.translatePhrase("(Hello) - \"world\"?!"));
    }

    @Test
    @DisplayName("Невідомі слова залишаються без змін")
    void testUnknownWordsRemainUnchanged() {
        assertEquals("Привіт unknown світ", translator.translatePhrase("Hello unknown world"));
    }

    @Test
    @DisplayName("Перевірка статусів додавання: ADDED, DUPLICATE та UPDATED")
    void testAddWordStatuses() {
        Translator localTranslator = new Translator();

        assertEquals(AddStatus.ADDED, localTranslator.addWord("hello", "привіт"));
        assertEquals(AddStatus.DUPLICATE, localTranslator.addWord("  HELLO  ", "привіт"));
        assertEquals(AddStatus.UPDATED, localTranslator.addWord("hello", "Привіт"));
    }

    @Test
    @DisplayName("Валідація помилкового введення (порожні рядки, речення, цифри, спецсимволи)")
    void testInvalidWordsThrowException() {
        assertThrows(IllegalArgumentException.class, () -> translator.addWord(null, "привіт"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello", null));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("   ", "привіт"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello", "   "));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("Hello, world!", "Привіт, світ!"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello1", "привіт"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello", "привіт!"));
        assertThrows(IllegalArgumentException.class, () -> translator.addWord("hello", "hello"));
    }

    @Test
    @DisplayName("Переклад порожньої фрази, null та нормалізація пробілів")
    void testEmptyBlankAndExtraSpacesPhrase() {
        assertEquals("", translator.translatePhrase(null));
        assertEquals("", translator.translatePhrase(""));
        assertEquals("", translator.translatePhrase("    "));
        assertEquals("Привіт світ джава", translator.translatePhrase("   Hello     world    java   "));
    }
}
```

*Результати тестування програми:*

<img src="" width="">

*Результати виконання Unit-тестів:*

<img src="" width="">

---
## Висновки
У результаті виконання лабораторної роботи було закріплено навички роботи з Java Collections Framework, зокрема
з асоціативним масивом `HashMap<String, String>` для ефективного зберігання та пошуку пар слів. 
Реалізовано валідацію вхідних даних, обробку виняткових ситуацій, збереження регістру й пунктуації під час перекладу фраз, 
а також покрито функціонал класу модульного тестування засобами JUnit 5.
