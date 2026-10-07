package model;

public class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }

    public double getBase() { return base; }
    public double getHeight() { return height; }

    @Override
    public double calcArea() {
        return 0.5 * base * height;
    }

    @Override
    public void draw() {
        System.out.println("Побудова трикутника. Основа: " + base + ", висота: " + height);
    }

    @Override
    public String toString() {
        return "Трикутник: " + super.toString() + ", основа: " + base + ", висота: " + height;
    }
}
