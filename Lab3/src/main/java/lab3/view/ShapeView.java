package lab3.view;

import lab3.model.Shape;

public class ShapeView {
    public void displayMessage(String message) {
        if (message != null && !message.isEmpty()) {
            System.out.println("\n" + message);
        }
    }
    public void displayShapes(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            System.out.println("Список фігур порожній.");
            return;
        }
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("%2d. %s%n", (i + 1), shapes[i]);
        }
    }
    // відображення початкового набору даних
    public void displayInitialShapes(Shape[] shapes) {
        displayMessage("Початковий набір фігур");
        displayShapes(shapes);
    }
    // метод друку повідомлень з готовими значеннями
    public void displayArea(double rectArea, double triangleArea,
                            double circleArea, double totalArea) {
        System.out.printf("Сумарна площа прямокутників: %.2f%n", rectArea);
        System.out.printf("Сумарна площа трикутників: %.2f%n", triangleArea);
        System.out.printf("Сумарна площа кіл: %.2f%n", circleArea);
        System.out.printf("Сумарна площа всіх фігур: %.2f%n", totalArea);
    }
    // метод відображення результатів за площею
    public void displaySortedByArea(Shape[] shapes) {
        displayMessage("Впорядкування за збільшенням площі (Comparator)");
        displayShapes(shapes);
    }
    // метод відображення результатів за кольором
    public void displaySortedByColor(Shape[] shapes) {
        displayMessage("Впорядкування за кольором (Comparator)");
        displayShapes(shapes);
    }

}