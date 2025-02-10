import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

/**
 Установку программы необходимо производить из Java кода с использованием класса File. Процесс будет состоять из следующих этапов:

 В папке Games создайте несколько директорий: src, res, savegames, temp.
 В каталоге src создайте две директории: main, test.
 В подкаталоге main создайте два файла: Main.java, Utils.java.
 В каталог res создайте три директории: drawables, vectors, icons.
 В директории temp создайте файл temp.txt.
 Файл temp.txt будет использован для записиси в него информации об успешноном или неуспешном создании файлов и директорий.

 Для работы с файлом или директорией необходимо создать для них свой экземпляр класса File, передав в конструктор адрес
 к файлу или директории. Создать директорию можно с помощью метода mkdir(), который возвращает true или false в зависимости
 от того, была ли директория создана успешно.
 Для создания файла можно использовать метод createNewFile(), который так же возвращает boolean. Обратите внимание,
 что этот метод пробрасывает IOException. Необходимо обернуть его в блок try-catch.
 Для сохранения лога используйте экземпляр класса StringBuilder. Добавляйте в него всю информацию о создании файлов и каталогов.
 Далее возьмите из него текст и запишите его в файл temp.txt с помощью класса FileWriter.
 В результате выполнения программы, на жестком диске комьютера в папке Games должны появиться вышеуказанные файлы и директории.
 Файл temp.txt должен содержать информацию о том, что все файлы были созданы успешно.*/

public class Installing {
    private StringBuilder sb = new StringBuilder();
    private File[] dirsAndFiles = {
            new File("C:/Games/savegames"),
            new File("C:/Games/src"),
            new File("C:/Games/res"),
            new File("C:/Games/temp"),
            new File("C:/Games/src/main"),
            new File("C:/Games/src/test"),
            new File("C:/Games/src/main/Main.java"),
            new File("C:/Games/src/main/Utils.java"),
            new File("C:/Games/res/drawables"),
            new File("C:/Games/res/vectors"),
            new File("C:/Games/res/icons"),
            new File("C:/Games/temp/temp.txt")};


    void install() throws IOException {
        Stream.of(dirsAndFiles)
                .forEach(x -> {
                    try {
                        if ((x.getPath().endsWith(".java") || x.getPath().endsWith(".txt")) && x.createNewFile()) {
                            sb.append("\nСоздан файл " + x.getPath());
                        } else if (x.mkdir()) {
                            sb.append("\nСоздана директория " + x.getPath());
                        } else {
                            sb.append("\nНе удалось создать файл/директорию " + x.getPath());
                        }
                    } catch (Exception e) {
                        sb.append("\nНе удалось создать файл/директорию " + x.getPath());
                        throw new RuntimeException("Не удалось создать файл/директорию " + x.getPath() + " "
                                + e.getMessage());
                    }
                });

        try (
                FileWriter fw = new FileWriter("C:/Games/temp/temp.txt")) {
            fw.write(sb.toString());
        } catch (Exception e) {
            throw new IOException("Не удалось записать файл: " + e.getMessage());
        }
    }

}