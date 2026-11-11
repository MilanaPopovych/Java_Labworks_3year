package lab5;

import lab5.file.*;
import java.io.*;

public class EncryptionApp {
    public static void main(String[] args) {
        String textToEncrypt = "Hello, Java World! Привіт Світ!";
        char key = 'K';
        String fileName = "encrypted.txt";
        // 1. Шифрування та запис у файл
        try (Writer fileWriter = new FileWriter(fileName);
             EncryptWriter encryptWriter = new EncryptWriter(fileWriter, key)) {

            encryptWriter.write(textToEncrypt);
            System.out.println("Текст успішно зашифровано і записано.");

        } catch (IOException e) {
            System.err.println("Помилка запису: " + e.getMessage());
        }
        // 2. Дешифрування та читання з файлу
        try (Reader fileReader = new FileReader(fileName);
             DecryptReader decryptReader = new DecryptReader(fileReader, key)) {

            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = decryptReader.read()) != -1) {
                sb.append((char) c);
            }
            System.out.println("Дешифрований текст з файлу:");
            System.out.println(sb.toString());

        } catch (IOException e) {
            System.err.println("Помилка читання: " + e.getMessage());
        }
    }
}
