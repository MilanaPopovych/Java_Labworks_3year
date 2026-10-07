package model;

public class Circle extends Shape {
    private double radius;
    public Circle(String shapeColor, double radius){
        super(shapeColor);
        this.radius = radius;
    }
    public double getRadius() { return radius; }

    @Override
    public double calcArea() {
        return Math.PI * Math.pow(radius, 2);
    }
    @Override
    public void draw(){
        System.out.println("Побудова кола. Радіус: " + radius);
    }
    @Override
    public String toString(){
        return "Коло: " + super.toString() + ", радіус: " + radius;
    }
}
