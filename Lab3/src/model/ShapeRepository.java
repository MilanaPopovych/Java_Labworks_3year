package model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ShapeRepository {
    private Shape[] shapes;
    public ShapeRepository(int size) {
        this.shapes = generateRandomShapes(Math.max(size, 10));
    }
    public Shape[] getShapes() {
        return shapes;
    }
    public double calculateTotalArea() {
        double total = 0.0;
        for (Shape shape : shapes) {
            total += shape.calcArea();
        }
        return total;
    }
    public double calculateAreaByType(Class<? extends Shape> shapeType) {
        double total = 0.0;
        for (Shape shape : shapes) {
            if (shapeType.isInstance(shape)) {
                total += shape.calcArea();
            }
        }
        return total;
    }
    public void sortByArea() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return Double.compare(s1.calcArea(), s2.calcArea());
            }
        });
    }
    public void sortByColor() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return s1.getShapeColor().compareToIgnoreCase(s2.getShapeColor());
            }
        });
    }
    private Shape[] generateRandomShapes(int count) {
        Shape[] data = new Shape[count];
        String[] colors = {"Червоний", "Синій", "Зелений", "Жовтий", "Білий", "Чорний"};
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String color = colors[random.nextInt(colors.length)];
            int shapeType = random.nextInt(3);

            switch (shapeType) {
                case 0:
                    double width = 1.0 + random.nextInt(15);
                    double height = 1.0 + random.nextInt(15);
                    data[i] = new Rectangle(color, width, height);
                    break;
                case 1:
                    double base = 1.0 + random.nextInt(15);
                    double triHeight = 1.0 + random.nextInt(15);
                    data[i] = new Triangle(color, base, triHeight);
                    break;
                case 2:
                    double radius = 1.0 + random.nextInt(10);
                    data[i] = new Circle(color, radius);
                    break;
            }
        }
        return data;
    }
}