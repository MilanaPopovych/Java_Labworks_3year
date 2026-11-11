package lab5.model;

import java.io.Serializable;

public abstract class Shape implements Drawable, Serializable {
    private final static long serialVersionUID = 1L;
    private final String shapeColor;
    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }
    public String getShapeColor() {
        return shapeColor;
    }
    public abstract double calcArea();

    @Override
    public String toString() {
        return "колір фігури -- [" + shapeColor + "], площа -- " +
                String.format("%.2f", calcArea());
    }
}