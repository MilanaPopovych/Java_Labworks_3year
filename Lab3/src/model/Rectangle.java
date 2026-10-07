package model;

public class Rectangle extends Shape {
    private double height;
    private double width;
    public Rectangle(String shapeColor, double height, double width) {
        super(shapeColor);
        this.height = height;
        this.width = width;
    }
    public double getHeight() { return height; }
    public double getWidth() { return width; }

    @Override
    public double calcArea() {
        return width * height;
    }
    @Override
    public void draw(){
        System.out.println("Побудова прямокутника розмірністю: " + width + " X " + height);
    }
    @Override
    public String toString(){
        return "Прямокутник: " + super.toString() + ", ширина: " + width + ", висота: " + height;
    }
}