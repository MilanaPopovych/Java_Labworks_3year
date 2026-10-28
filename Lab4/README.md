# Лабораторна робота № 4 ІО-46 Попович Мілана
## Тема: Використання узагальненого програмування
---

## Завдання
Реалізувати із застосування узагальненого програмування ієрархію Java-класів для
вольєрів для різних видів тварин. \
У зоопарку є наступні види тварин: птахи (орли) та ссавці (леви, зебри, жирафи).
Для цих тварин передбачено вольєри: вольєри для левів, вольєри для копитних (зебр
та жирафів), вольєри для птахів. \
У вольєрі для левів можна розмістити тільки левів. У вольєрі для птахів – тільки
птахів. У вольєрі для копитних – тільки зебр та жирафів. Реалізувати на основі узагальненого
програмування (generics) вказані обмеження щодо розміщення тварин. \
Для класів вольєрів реалізувати наступні функції:
* Кожен вольєр має максимальну ємність (кількість тварин).
* Реалізувати функцію для отримання максимальної кількості місць та функцію для отримання кількості зайнятих місць.
* Розміщення тварини у вольєрі. Якщо всі м’ясця у вольєрі зайнято, функція повинна ініціювати виключну ситуацію.
* Вилучення тварини із вольєра. Функція повинна ініціювати виключну ситуацію, якщо
вказаної тварини немає у вольєрі.

Реалізувати модульні тести з наповнення вольєрів.

---
## Виконання завдання
*Опис програми:*
1. Організація коду: поділ класів на пакети за суттю (тварини, типи тварин, вольєри тощо);
2. Клас Zoo: головний клас, зберігає різні типи вольєрів та забезпечує підрахунок тварин;
3. Реалізація базового класу `Cage<T>` та його нащадків для контролю розміщення;
4. Кастомні винятки для окремих сценаріїв (розміщення тварини в повному вольєрі та видалення неіснуючої);
5. Клас ZooTest: Тестовий клас для перевірки коректності роботи методів через модульні тести. 

*UML-діаграма класів*

<img src="" width="600">

**Лістинг коду**

* Файл Zoo.java:
```java
package zoo.core;

import zoo.animaltypes.Animal;
import zoo.cages.Cage;

import java.util.ArrayList;
import java.util.List;

public class Zoo {
    public List<Cage<? extends Animal>> cages = new ArrayList<>();

    public int getCountOfAnimals() {
        int total = 0;
        for (Cage<? extends Animal> cage : cages) {
            total += cage.getOccupiedCapacity();
        }
        return total;
    }

    public void addCage(Cage<? extends Animal> cage) {
        cages.add(cage);
    }
}

```

* Файл ZooTest.java
```java
import org.junit.jupiter.api.Test;
import zoo.animals.*;
import zoo.cages.*;
import zoo.core.Zoo;
import zoo.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

public class ZooTest {
    @Test
    public void testAddAndRemoveAnimal() throws Exception {
        LionCage lionCage = new LionCage(2);
        Lion simba = new Lion("Сімба");

        lionCage.addAnimal(simba);
        assertEquals(1, lionCage.getOccupiedCapacity());

        lionCage.removeAnimal(simba);
        assertEquals(0, lionCage.getOccupiedCapacity());
    }

    @Test
    public void testCageFullException() throws Exception {
        LionCage lionCage = new LionCage(1);
        lionCage.addAnimal(new Lion("Сімба"));

        // перевіряємо, чи генерується виключення при переповненні
        assertThrows(CageFullException.class, () -> {
            lionCage.addAnimal(new Lion("Муфаса"));
        });
    }

    @Test
    public void testAnimalNotFoundException() {
        LionCage lionCage = new LionCage(2);
        Lion simba = new Lion("Сімба");

        // перевіряємо, чи генерується виключення при видаленні відсутньої тварини
        assertThrows(AnimalNotFoundException.class, () -> {
            lionCage.removeAnimal(simba);
        });
    }

    @Test
    public void testUngulateCageAcceptsZebrasAndGiraffes() throws Exception {
        UngulateCage ungulateCage = new UngulateCage(5);
        Zebra zebra = new Zebra("Марті");
        Giraffe giraffe = new Giraffe("Мелман");

        ungulateCage.addAnimal(zebra);
        ungulateCage.addAnimal(giraffe);

        assertEquals(2, ungulateCage.getOccupiedCapacity());


//        Помилка компіляції для перевірки роботи генериків
//        ungulateCage.addAnimal(new Lion("Алекс"));

    }

    @Test
    public void testZooTotalAnimalsCount() throws Exception {
        Zoo zoo = new Zoo();

        LionCage lionCage = new LionCage(3);
        lionCage.addAnimal(new Lion("Лев 1"));
        lionCage.addAnimal(new Lion("Лев 2"));

        UngulateCage ungulateCage = new UngulateCage(4);
        ungulateCage.addAnimal(new Zebra("Зебра 1"));
        ungulateCage.addAnimal(new Giraffe("Жираф 1"));

        BirdCage birdCage = new BirdCage(5);
        birdCage.addAnimal(new Eagle("Орел 1"));

        zoo.addCage(lionCage);
        zoo.addCage(ungulateCage);
        zoo.addCage(birdCage);
        // Перевірка загальної кількості тварин
        assertEquals(5, zoo.getCountOfAnimals());
    }
}
```

*Результати тестування програми:*

Модульні тести (JUnit) -- інтерфейс IntelliJ :
<img src="" width="700">

-- система збірки Maven:
<img src="" width="700">

Приклад помилки компіляції (розміщення льва у вольєрі з копитними): 
<img src="" width="700">

---
## Висновки
У результаті виконання лабораторної роботи було розроблено ієрархію Java-класів для моделювання зоопарку з застосуванням механізмів узагальненого програмування (generics).
Реалізовано модульні тести для перевірки надійності розробленого коду. 
