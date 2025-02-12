import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Таким образом, для выполнения задания потребуется проделать следующие шаги:
 * <p>
 * Создать три экземпляра класса GameProgress.
 * Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
 * Созданные файлы сохранений из папки savegames запаковать в один архив zip.
 * Удалить файлы сохранений, лежащие вне архива.
 * Реализация
 * Создайте три экземпляра класса GameProgress.
 * <p>
 * Реализуйте метод saveGame(), принимающий в качестве аргументов полный путь к файлу типа String
 * (например, "/Users/admin/Games/GunRunner/savegames/save3.dat") и объект класса GameProgress.
 * Для записи Вам потребуются такие классы как FileOutputStream и ObjectOutputStream. У последнего есть метод
 * writeObject(), подходящий для записи сериализованного объекта . Во избежание утечек памяти, не забудьте либо
 * использовать try с ресурсами, либо вручную закрыть файловые стримы (это касается всех случаев работы с файловыми потоками).
 * Таким образом, вызов метода saveGame() должен приводить к созданию файлов сохранений в папке savegames.
 * <p>
 * Далее реализуйте метод zipFiles(), принимающий в качестве аргументов String полный путь к файлу архива
 * (например, "/Users/admin/Games/GunRunner/savegames/zip.zip") и список запаковываемых объектов в виде списка строчек
 * String полного пути к файлу (например, "/Users/admin/Games/GunRunner/savegames/save3.dat"). В методе Вам потребуется
 * реализовать блок try-catch с объектами ZipOutputStream и FileOutputStream. Внутри него пробегитесь по списку файлов и
 * для каждого организуйте запись в блоке try-catch через FileInputStream. Для этого создайте экземпляр ZipEntry и
 * уведомьте ZipOutputStream о новом элементе архива с помощью метода putNextEntry(). Далее необходимо считать
 * упаковываемый файл с помощью метода read() и записать его с помощью метода write(). После чего уведомьте
 * ZipOutputStream о записи файла в архив с помощью метода closeEntry().
 * <p>
 * Далее, используя методы класса File, удалите файлы сохранений, не лежащие в архиве.
 */
public class Main {
    // переменная для нумерации сохраненных игр
    static int countSavings = 0;
    // список объектов, доступный всем методам
    static ArrayList<String> gamesForZip = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String pathForWrite = "C:/Games/savegames";

        Installing installing = new Installing();
        installing.install();

        GameProgress gameProgress = new GameProgress(30, 2, 9, 35.4);
        GameProgress gameProgress1 = new GameProgress(75, 2, 14, 115.1);
        GameProgress gameProgress2 = new GameProgress(17, 3, 16, 12.8);

        saveGame(pathForWrite, gameProgress);
        saveGame(pathForWrite, gameProgress1);
        saveGame(pathForWrite, gameProgress2);

        File saveDir = new File(pathForWrite);
        for (File file : saveDir.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }

    static void saveGame(String pathForWrite, GameProgress gameProgress) throws IOException {
        String fileName = pathForWrite + "/save" + countSavings + ".dat";
        File zipDir = new File("C:/Games/savegames/zip.zip");
        zipDir.mkdir();
        System.out.println(fileName);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {


            oos.writeObject(gameProgress);
            gamesForZip.add(fileName);
            zipFiles(zipDir.getPath(), gamesForZip);
            countSavings++;

        } catch (Exception e) {
            throw new IOException("Не удалось сохранить файл: " + e.getMessage());
        }
    }

    static void zipFiles(String pathForZip, List<String> gamesForZip) throws FileNotFoundException, IOException {
        String path = gamesForZip.get(countSavings);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathForZip));
             FileInputStream fis = new FileInputStream(path)) {
            ZipEntry zipEntry = new ZipEntry("zipped_save" + countSavings + ".txt");
            zos.putNextEntry(zipEntry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            System.out.println(Arrays.toString(buffer));
            zos.write(buffer);
            System.out.println(zipEntry);
            zos.closeEntry();

        } catch (Exception e) {
            throw new RuntimeException(e.getClass() + "Не удалось архивировать файл: " + e.getMessage());
        }
    }
}