package lab3;

import lab3.controller.ShapeController;
import lab3.model.ShapeRepository;
import lab3.view.ShapeView;

public class Main {
    public static void main(String[] args) {
        ShapeRepository model = new ShapeRepository(12); // масив з 12 ел.-тів
        ShapeView shapeView = new ShapeView();
        ShapeController controller = new ShapeController(model, shapeView);
        controller.run();
    }
}