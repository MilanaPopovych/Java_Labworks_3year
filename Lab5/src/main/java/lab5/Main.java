package lab5;

import lab5.controller.ShapeController;
import lab5.model.ShapeRepository;
import lab5.view.ShapeView;

public class Main {
    public static void main(String[] args) {
        ShapeRepository model = new ShapeRepository(12); // масив з 12 ел.-тів
        ShapeView shapeView = new ShapeView();
        ShapeController controller = new ShapeController(model, shapeView);
        controller.run();
    }
}