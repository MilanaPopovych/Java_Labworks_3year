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