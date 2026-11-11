package lab5.view;

import lab5.model.Shape;
import java.util.Scanner;

public class ShapeView {
    private final Scanner scanner = new Scanner(System.in);
    // загальні методи представлення
    public void displayMessage(String message) {
        System.out.println("\n" + message);
    }
    public void displayShapes(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            System.out.println("Список фігур порожній або відсутній.");
            return;
        }
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("%2d. %s%n", (i + 1), shapes[i]);
        }
    }
    //  форматування результатів обчислення площ
    public void displayAreaStatistics(double rectArea, double triangleArea, double circleArea, double totalArea) {
        System.out.println("\n**** Результати обчислення площ ****");
        System.out.printf("Сумарна площа прямокутників: %.2f%n", rectArea);
        System.out.printf("Сумарна площа трикутників:   %.2f%n", triangleArea);
        System.out.printf("Сумарна площа кіл:           %.2f%n", circleArea);
        System.out.printf("Сумарна площа всіх фігур:    %.2f%n", totalArea);
    }
    // форматування результатів сортування
    public void displaySortedByArea(Shape[] shapes) {
        System.out.println("\nВпорядкування за збільшенням площі (Comparator):");
        displayShapes(shapes);
    }
    public void displaySortedByColor(Shape[] shapes) {
        System.out.println("\nВпорядкування за кольором (Comparator):");
        displayShapes(shapes);
    }
    // меню + зчитування шляхів
    public String showMenuAndGetChoice() {
        System.out.println("\n[****** МЕНЮ ******]");
        System.out.println("1. Показати поточні фігури");
        System.out.println("2. Обчислити сумарні площі");
        System.out.println("3. Сортувати за площею");
        System.out.println("4. Сортувати за кольором");
        System.out.println("5. Зберегти фігури у файл");
        System.out.println("6. Завантажити фігури з файлу");
        System.out.println("7. Пошук фігур за мінімальною площею");
        System.out.println("0. Вихід");
        System.out.print("Оберіть команду: ");

        return scanner.nextLine();
    }
    public String getSaveFilePathInput() {
        System.out.print("Введіть шлях/ім'я файлу для збереження: ");
        return scanner.nextLine();
    }
    public String getLoadFilePathInput() {
        System.out.print("Введіть шлях/ім'я файлу для зчитування: ");
        return scanner.nextLine();
    }
    public void displayInvalidInputMessage() {
        System.out.println("Некоректне введення, спробуйте ще раз.");
    }
    // Зчитування числа з клавіатури із захистом try-catch
    public double getSearchAreaInput() {
        while (true) {
            System.out.print("Введіть мінімальну площу для пошуку: ");
            try {
                String input = scanner.nextLine();
                // Намагаємося перетворити введений рядок на число
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                // Обробка некоректного введення
                System.out.println("Помилка! Будь ласка, введіть коректне число (наприклад: 15.5 або 20).");
            }
        }
    }
    public void displaySearchResults(Shape[] foundShapes) {
        System.out.println("\n--- Результати пошуку ---");
        if (foundShapes.length == 0) {
            System.out.println("Фігур із заданими критеріями не знайдено.");
        } else {
            displayShapes(foundShapes);
        }
    }
}