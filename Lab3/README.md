# Лабораторна робота № 3 ІО-46 Попович Мілана
## Тема: Використання ООП та шаблону MVC
---

## Завдання
Напишіть консольний додаток, використовуючи архітектурний шаблон
MVC, який: 
1. описує інтерфейс Drawable з методом побудови фігури draw();
2. описує абстрактний клас Shape, який реалізує інтерфейс Drawable і
містить поле shapeColor типу String для кольору фігури і конструктор
для його ініціалізації, абстрактний метод обчислення площі фігури
calcArea() і перевизначений метод toString();
3. описує класи Rectangle, Triangle, Circle, які успадковуються від класу
Shape і реалізують метод calcArea (), а також перевизначають метод
toString ();
4. створює набір даних типу Shape (масив розмірністю не менш 10
елементів);
5. обробляє масив:
- відображає набір даних;
- обчислює сумарну площу всіх фігур набору даних;
- обчислює сумарну площу фігур заданого виду;
- впорядковує набір даних щодо збільшення площі фігур,
використовуючи об'єкт інтерфейсу Comparator;
- впорядковує набір даних за кольором фігур, використовуючи об'єкт
інтерфейсу Comparator.

Значення для ініціалізації об'єктів вибираються з заздалегідь підготовлених
даних (обраних випадковим чином або по порядку проходження).

---
## Виконання завдання
*Опис програми:*
1. Організація коду: поділ класів на групи за технічною роллю (model, view, controller);
* Model: інкапсуляція геометричних сутностей (Rectangle, Triangle, Circle), класи з методами розрахунків площ та роботи з масивом даних. 
* View: форматування та вивід інформації в консоль.
* Controller: запит даних у репозиторію (ShapeRepository), передача результатів у View.
2. Клас Main: створює екземпляри моделі, представлення й контролера, передає керування методу run().
3. Механізм сортування з реалізацією компараторів: за числовим значенням площі фігур, назвою кольору.

*UML-діаграма класів*

<img src="https://github.com/MilanaPopovych/Java_Labworks_3year/blob/a4334193e59b97c01a50a9296a080c4b8d6fb938/Lab3/img/src1.png" width="600">

*Лістинг коду*

* Файл ShapeRepository.java:
```java
package model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ShapeRepository {
    private Shape[] shapes;
    public ShapeRepository(int size) {  // ініц. сховище об'єктів
        this.shapes = generateRandomShapes(Math.max(size, 10));
    }
    public Shape[] getShapes() {
        return shapes;
    }

    // розрахунок сумарної площі всіх елементів  
    public double calculateTotalArea() {
        double total = 0.0;
        for (Shape shape : shapes) {
            total += shape.calcArea();
        }
        return total;
    }

    // розрахунок площі фігур окремих типів
    public double calculateAreaByType(Class<? extends Shape> shapeType) {
        double total = 0.0;
        for (Shape shape : shapes) {
            if (shapeType.isInstance(shape)) {
                total += shape.calcArea();
            }
        }
        return total;
    }

    // cортування масиву фігур за збільшенням площі
    public void sortByArea() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return Double.compare(s1.calcArea(), s2.calcArea());
            }
        });
    }

    // сортування масиву фігур за назвою кольору
    public void sortByColor() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return s1.getShapeColor().compareToIgnoreCase(s2.getShapeColor());
            }
        });
    }

    // наповнення масиву тестовими даними
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
```
* Файл ShapeController.java
```java
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
```

*Результати тестування програми:*

<img src="https://github.com/MilanaPopovych/Java_Labworks_3year/blob/a4334193e59b97c01a50a9296a080c4b8d6fb938/Lab3/img/%D0%97%D0%BD%D1%96%D0%BC%D0%BE%D0%BA%20%D0%B5%D0%BA%D1%80%D0%B0%D0%BD%D0%B0%202026-10-07%20061952.png" width="700">
<img src="https://github.com/MilanaPopovych/Java_Labworks_3year/blob/a4334193e59b97c01a50a9296a080c4b8d6fb938/Lab3/img/%D0%97%D0%BD%D1%96%D0%BC%D0%BE%D0%BA%20%D0%B5%D0%BA%D1%80%D0%B0%D0%BD%D0%B0%202026-10-07%20062005.png" width="700">
<img src="https://github.com/MilanaPopovych/Java_Labworks_3year/blob/a4334193e59b97c01a50a9296a080c4b8d6fb938/Lab3/img/%D0%97%D0%BD%D1%96%D0%BC%D0%BE%D0%BA%20%D0%B5%D0%BA%D1%80%D0%B0%D0%BD%D0%B0%202026-10-07%20062016.png" width="700">

---
## Висновки
У результаті виконання лабораторної роботи було розроблено програму було розроблено консольний додаток мовою програмування Java із застосуванням архітектурного шаблону MVC (Model-View-Controller) для обробки та аналізу набору геометричних фігур. 
