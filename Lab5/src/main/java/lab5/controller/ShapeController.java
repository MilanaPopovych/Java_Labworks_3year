package lab5.controller;

import lab5.file.FileManager;
import lab5.model.*;
import lab5.view.*;

public class ShapeController {
    private final ShapeRepository repository;
    private final ShapeView view;

    public ShapeController(ShapeRepository repository, ShapeView view) {
        this.repository = repository;
        this.view = view;
    }

    public void run() {
        boolean running = true;
        while (running) {
            String choice = view.showMenuAndGetChoice();

            switch (choice) {
                case "1":
                    view.displayShapes(repository.getShapes());
                    break;
                case "2":
                    double rectArea = repository.calculateAreaByType(Rectangle.class);
                    double triangleArea = repository.calculateAreaByType(Triangle.class);
                    double circleArea = repository.calculateAreaByType(Circle.class);
                    double totalArea = repository.calculateTotalArea();

                    view.displayAreaStatistics(rectArea, triangleArea, circleArea, totalArea);
                    break;
                case "3":
                    repository.sortByArea();
                    view.displaySortedByArea(repository.getShapes());
                    break;
                case "4":
                    repository.sortByColor();
                    view.displaySortedByColor(repository.getShapes());
                    break;
                case "5":
                    String savePath = view.getSaveFilePathInput();
                    FileManager.saveShapesToFile(savePath, repository.getShapes());
                    break;
                case "6":
                    String loadPath = view.getLoadFilePathInput();
                    Shape[] loadedShapes = FileManager.loadShapesFromFile(loadPath);
                    if (loadedShapes != null) {
                        repository.setShapes(loadedShapes);
                    }
                    break;
                case "7":
                    double minArea = view.getSearchAreaInput();
                    Shape[] foundShapes = repository.searchByMinArea(minArea);
                    view.displaySearchResults(foundShapes);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    view.displayInvalidInputMessage();
            }
        }
    }
}