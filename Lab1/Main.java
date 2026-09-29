package com.example.app;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // entry point
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть слова через пробіл: ");
        String enteredText = scanner.nextLine();
        String[] words = enteredText.trim().split("\\s+"); //розбиття тексту на масив рядків
        /* тестовий масив
        String[] words = {"Hello", "world", "it's", "me"};
         */
        String[] shorterWords = filterByAvgLen(words, true);
        System.out.println("Рядки, довжина яких менша за середню: " + Arrays.toString(shorterWords));
        String[] longerWords = filterByAvgLen(words, false);
        System.out.println("Рядки, довжина яких більша за середню: " + Arrays.toString(longerWords));
        scanner.close();
    }
    // фільтрація рядків по довжині
    public static String[] filterByAvgLen (String[] input, boolean findShorterThanAvg) {
        // перевірка на порожній масив
        if (input ==  null || input.length == 0) {
            return new String[0];
        }
        int totalChars = 0; // сума довжин всіх рядків
        for (String str : input) {
            if (str != null) { totalChars += str.length(); }
        }
        double avgLen = (double) totalChars / input.length; // сер. арифм. довжин
        List<String> resultList = new ArrayList<>(); // динамічний список
        for (String str : input) {
            if (str != null) { if (findShorterThanAvg && str.length() < avgLen) { resultList.add(str); }
            else if (!findShorterThanAvg && str.length() > avgLen) resultList.add(str); }
        }
        return resultList.toArray(new String[0]);
    }
}
