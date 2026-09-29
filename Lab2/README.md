# Лабораторна робота № 2 ІО-46 Попович Мілана
## Тема: Робота з класами
---

## Завдання
1. Реалізувати метод equals для класу Person у якого є декілька полів (прізвище, ім’я, вік).
2. Реалізуйте наступний сценарій:
* Створіть екземпляр Person
* Конвертуйте його в JSON*
* Конвертуйте назад в об’єкт*
* Перевірте equals-ом початковий і одержаний об'єкти

*Для серіалізації та десеріалізації в/з JSON можна використовувати бібліотеку gson
(https://sites.google.com/site/gson/gson-user-guide).

Бажано!
Реалізуйте unit tests для методу equals за допомогою бібліотеки equals verifier

---
## Виконання завдання
*Опис програми:*
1. Налаштування залежностей в конфігураційному файлі pom.xml (сторонні бібліотеки gson, JUnit, EqualsVerifier)
2. Клас Person з 3-ма полями (дані типу String та int), конструктор класу та метод для порівняння об'єктів `equals`
3. Клас Main з викликом конструктора Person (створення екземпляра), методів серіалізації/десеріалізації та перевірки рівності екземплярів.
4. Тестовий клас PersonTest для перевірки коректності реалізації методів equals, hashCode.

*Роздруківка коду програми:*
* Файл Person.java:
```java
package lab2;

import java.util.Objects;

public class Person {
    private String surname;
    private String name;
    private int age;
    public Person() {}
    // конструктор класу
    public Person (String surname, String name, int age) {
        this.surname = surname;
        this.name = name;
        this.age = age;
    }
    // метод для порівняння об'єктів
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // порівняння об'єкта самого з собою
        if (obj == null || getClass() != obj.getClass()) return false; // перевірка на null та класу
        Person person = (Person) obj; // приведення типу
        return age == person.age && Objects.equals(surname, person.surname) &&
                Objects.equals(name, person.name);
    }
    // метод перетворення даних об'єкта на int
    @Override
    public int hashCode() {
        return Objects.hash(surname, name, age); }
    // метод перетворення об'єкта на рядок
    @Override
    public String toString() {
        return "Person{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

* Файл Main.java: 
```java
package lab2;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        // a. створення екземпляра
        Person pOrig = new Person("Попович", "Мілана", 19);
        System.out.println("Початковий екземпляр Person: " + pOrig);
        // b. конвертація в JSON
        String toJson = gson.toJson(pOrig);
        System.out.println("JSON рядок: " + toJson);
        // c. конвертація назад в об'єкт
        Person pDes =  gson.fromJson(toJson, Person.class);
        System.out.println("Десериалізований об'єкт: " + pDes);
        // d. перевірка за допомогою equals
        boolean areEqual = pOrig.equals(pDes);
        System.out.println("\nРезультати перевірки: ");
        System.out.println("Чи однаковий зміст об'єкта? Відповідь: " + areEqual);
        System.out.println("Чи однакове посилання об'єкта? Відповідь: " + (pOrig == pDes));
    }
}
```
* Файл PersonTest.java:
```java
package lab2;

import org.junit.jupiter.api.Test;
import nl.jqno.equalsverifier.EqualsVerifier;

class PersonTest {
    @Test
    void testEqualsAndHash() {
        EqualsVerifier.simple().forClass(Person.class).verify();
    }
}
```

*Результати тестування програми:*

Запуск тесту в середовищі IntelliJ:

<img src="https://github.com/MilanaPopovych/Java_Labworks_3year/blob/0849f1a839e4a39221ffee571ae92188b5ac1f1e/Lab2/img/%D0%97%D0%BD%D1%96%D0%BC%D0%BE%D0%BA%20%D0%B5%D0%BA%D1%80%D0%B0%D0%BD%D0%B0%202026-09-29%20232023.png" width="800">

Запуск тесту через термінал (`mvn test`): 

<img src="https://github.com/MilanaPopovych/Java_Labworks_3year/blob/0849f1a839e4a39221ffee571ae92188b5ac1f1e/Lab2/img/%D0%97%D0%BD%D1%96%D0%BC%D0%BE%D0%BA%20%D0%B5%D0%BA%D1%80%D0%B0%D0%BD%D0%B0%202026-09-29%20212600.png" width="800">

---
## Висновки
У результаті виконання лабораторної роботи було розроблено програму з методами для логічного порівняння об'єктів, перевірки коректності десеріалізації та автоматизованого модульного тестування із застосуванням сторонніх бібліотек.
