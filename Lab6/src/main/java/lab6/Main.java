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