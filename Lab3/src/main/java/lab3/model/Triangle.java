package lab3.model;

public class Triangle extends Shape {
    private final double base;
    private final double height;

    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }
    // геттери (основа та висота)
    public double getBase() { return base; }
    public double getHeight() { return height; }
    // метод підрахунку площі трикутника
    @Override
    public double calcArea() {
        return 0.5 * base * height;
    }
    // виклик методу виведення фігури (draw)
    @Override
    public void draw() {
        System.out.println("Побудова трикутника. Основа: " + base + ", висота: " + height);
    }
    // виклик перевизначеного toString
    @Override
    public String toString() {
        return "Трикутник: " + super.toString() + ", основа: " + base + ", висота: " + height;
    }
}
