package controller;

import model.*;
import view.*;

public class ShapeController {
    private ShapeRepository repository;
    private ShapeView view;
    public ShapeController(ShapeRepository repository,
                           ShapeView view) {
        this.repository = repository;
        this.view = view;
    }
    public void run() {
        // поч. набір даних
        view.displayMessage("Початковий набір фігур");
        view.displayShapes(repository.getShapes());

        // сумарна площа всіх фігур
        double totalArea = repository.calculateTotalArea();

        // сумарна площа за видами фігур
        double rectArea = repository.calculateAreaByType(Rectangle.class);
        double triangleArea = repository.calculateAreaByType(Triangle.class);
        double circleArea = repository.calculateAreaByType(Circle.class);
        view.displayArea("Сумарна площа прямокутників", rectArea);
        view.displayArea("Сумарна площа трикутників", triangleArea);
        view.displayArea("Сумарна площа кіл", circleArea);

        // сортування за зростанням площі
        view.displayMessage("Впорядкування за збільшенням площі (Comparator)");
        repository.sortByArea();
        view.displayShapes(repository.getShapes());

        // сортування за кольором
        view.displayMessage("Впорядкування за кольором (Comparator)");
        repository.sortByColor();
        view.displayShapes(repository.getShapes());
    }
}
