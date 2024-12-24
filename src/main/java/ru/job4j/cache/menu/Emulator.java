package ru.job4j.cache.menu;

import ru.job4j.cache.DirFileCache;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Emulator {

    private static DirFileCache cache;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. Указать кэшируемую директорию");
            System.out.println("2. Загрузить содержимое файла в кэш");
            System.out.println("3. Получить содержимое файла из кэша");
            System.out.println("4. Выход");
            System.out.print("Выберите опцию: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: введите число от 1 до 4.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите путь к кэшируемой директории: ");
                    String dir = scanner.nextLine().trim();
                    if (dir.isEmpty()) {
                        System.out.println("Ошибка: путь не может быть пустым.");
                        break;
                    }
                    if (!Files.isDirectory(Paths.get(dir))) {
                        System.out.println("Ошибка: директория не существует или указан некорректный путь.");
                        break;
                    }
                    cache = new DirFileCache(dir);
                    System.out.println("Кэшируемая директория установлена.");
                    break;
                case 2:
                    if (cache == null) {
                        System.out.println("Сначала укажите кэшируемую директорию.");
                        break;
                    }
                    System.out.print("Введите имя файла для загрузки в кэш: ");
                    String fileToLoad = scanner.nextLine().trim();
                    if (fileToLoad.isEmpty()) {
                        System.out.println("Ошибка: имя файла не может быть пустым.");
                        break;
                    }
                    if (!Files.exists(Paths.get(cache.getCachingDir(), fileToLoad))) {
                        System.out.println("Ошибка: файл не существует в указанной директории.");
                        break;
                    }
                    cache.get(fileToLoad);
                    System.out.println("Файл загружен в кэш.");
                    break;
                case 3:
                    if (cache == null) {
                        System.out.println("Сначала укажите кэшируемую директорию.");
                        break;
                    }
                    System.out.print("Введите имя файла для получения из кэша: ");
                    String fileToGet = scanner.nextLine().trim();
                    if (fileToGet.isEmpty()) {
                        System.out.println("Ошибка: имя файла не может быть пустым.");
                        break;
                    }
                    String content = cache.get(fileToGet);
                    if (content == null) {
                        System.out.println("Файл не найден в кэше или его содержимое пусто.");
                    } else {
                        System.out.println("Содержимое файла: " + content);
                    }
                    break;
                case 4:
                    running = false;
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор, введите число от 1 до 4.");
            }
        }
    }
}
