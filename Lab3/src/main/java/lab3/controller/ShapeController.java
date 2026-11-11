package lab3.controller;

import lab3.model.*;
import lab3.view.*;

public class ShapeController {
    private final ShapeRepository repository;
    private final ShapeView view;

    public ShapeController(ShapeRepository repository, ShapeView view) {
        this.repository = repository;
        this.view = view;
    }

    public void run() {
        view.displayInitialShapes(repository.getShapes());
        view.displayArea(
                repository.calculateAreaByType(Rectangle.class),
                repository.calculateAreaByType(Triangle.class),
                repository.calculateAreaByType(Circle.class),
                repository.calculateTotalArea()
        );
        repository.sortByArea();
        view.displaySortedByArea(repository.getShapes());
        repository.sortByColor();
        view.displaySortedByColor(repository.getShapes());
    }
}
