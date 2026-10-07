package main.java;

import controller.ShapeController;
import model.ShapeRepository;
import view.ShapeView;

public class Main {
    public static void main(String[] args) {
        ShapeRepository model = new ShapeRepository(12); // масив з 12 ел.-тів
        ShapeView shapeView = new ShapeView();
        ShapeController controller = new ShapeController(model, shapeView);
        controller.run();
    }
}