package view;

import model.Shape;

public class ShapeView {
    public void displayMessage(String message) {
        System.out.println("\n" + message);
    }
    public void displayShapes(Shape[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("%2d. %s%n", (i + 1), shapes[i]);
        }
    }
    public void displayArea(String title, double area) {
        System.out.printf("%s: %.2f%n", title, area);
    }
}