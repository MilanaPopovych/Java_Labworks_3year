# Лабораторна робота № 1 ІО-46 Попович Мілана
## Тема: Робота з циклами, масивами та рядками в Java.
---
## Завдання
Відповідно до номера у загальному списку слухачів дисципліни створити консольний додаток:
1. Визначити ті рядки, довжина яких менша (більша) середньої. На вхід поступає масив String. На виході – масив String.
---
## Виконання завдання
*Опис програми:*
1. Перевірка вводу на порожній масив
2. Прохід циклом for-each по масиву, сумування довжин рядків та ділення суми на к-сть рядків (середнє арифметичне довжин)
3. Ще один цикл, порівняти довжину кожного рядка із середнім значенням (строго більше або менше)
4. Рядки, що задовільняють умову, додати до ArrayList
5. Сформувати та повернути масив рядків.

*Роздруківка коду програми:*
```java
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
```
*Результати тестування програми:*

Сценарій 1: масив з n к-стю елементів

Сценарій 2: порожній масив

Сценарій 3: масив з рядками, довжина яких дорівнює середній

---
## Висновки
У результаті виконання лабораторної роботи було розроблено консольний додаток за індивідуальним варіантом для обробки та фільтрації даних типу String за критерієм відхилення від середньої довжини. 
