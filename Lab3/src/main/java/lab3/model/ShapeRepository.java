package lab3.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ShapeRepository {
    private final Shape[] shapes;
    public ShapeRepository(int size) {
        this.shapes = generateRandomShapes(Math.max(size, 10));
    }
    public Shape[] getShapes() {
        return Arrays.copyOf(shapes, shapes.length);
    }
    // метод обчислення загальної суми площ всіх фігур
    public double calculateTotalArea() {
        double total = 0.0;
        for (Shape shape : shapes) {
            total += shape.calcArea();
        }
        return total;
    }
    // метод обчислення суми площ певних фігур
    public double calculateAreaByType(Class<? extends Shape> shapeType) {
        double total = 0.0;
        for (Shape shape : shapes) {
            if (shapeType.isInstance(shape)) {
                total += shape.calcArea();
            }
        }
        return total;
    }
    // метод сортування фігур за зростанням площ
    public void sortByArea() {
        Arrays.sort(shapes, Comparator.comparingDouble(Shape::calcArea));
    }
    // метод сортування фігур за кольором
    public void sortByColor() {
        Arrays.sort(shapes, (s1, s2) -> s1.getShapeColor().compareToIgnoreCase(s2.getShapeColor()));
    }
    // метод генерації фігур з параметрами
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
                    data[i] = new Rectangle(color, height, width);
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