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
        //
        if (existingTranslation.equals(cleanUkr)) {
            return AddStatus.DUPLICATE;
        }
        //
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