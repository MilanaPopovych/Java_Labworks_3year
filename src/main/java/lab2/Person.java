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