package lab5.file;

import lab5.model.Shape;
import java.io.*;

public class FileManager {
    public static void saveShapesToFile(String filePath, Shape[] shapes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(shapes);
            System.out.println("Масив фігур збережено у файл: " + filePath);
        } catch (IOException e) {
            System.err.println("Помилка при збереженні у файл: " + e.getMessage());
        }
    }

    public static Shape[] loadShapesFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Shape[] shapes = (Shape[]) ois.readObject();
            System.out.println("Масив фігур завантажено з файлу: " + filePath);
            return shapes;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Помилка при зчитуванні з файлу: " + e.getMessage());
            return null;
        }
    }
}
