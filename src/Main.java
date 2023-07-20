import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) {

        GameProgress gameProgressPetya = new GameProgress(100, 30, 1, 144);
        GameProgress gameProgressKolya = new GameProgress(54, 2, 14, 1244);
        GameProgress gameProgressOlya = new GameProgress(115, 300, 94, 9312);

        List<String> savePath = new ArrayList<>();
        savePath.add("/Users/Shared/Games/savegames/save1.dat");
        savePath.add("/Users/Shared/Games/savegames/save2.dat");
        savePath.add("/Users/Shared/Games/savegames/save3.dat");

        saveGame(savePath.get(0), gameProgressPetya);
        saveGame(savePath.get(1), gameProgressKolya);
        saveGame(savePath.get(2), gameProgressOlya);

        File zipFile = new File("/Users/Shared/Games/savegames/save.zip");
        zipFiles(zipFile.getAbsolutePath(), savePath);

        File dir = new File("/Users/Shared/Games/savegames/");
        for (File item : dir.listFiles()) {
            if (!item.isDirectory()) {
                if (!item.equals(zipFile)) {
                    item.delete();
                    System.out.println("Файл " + item.getName() + " удален!");
                }
            }
        }
    }

    public static void saveGame(String filePath, GameProgress gameProgressData) {
        // откроем выходной поток для записи в файл
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // запишем экземпляр класса в файл
            oos.writeObject(gameProgressData);
            System.out.println("Файл " + filePath + " сохранен!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> filesPath) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath));) {
            for (String savePathItem : filesPath) {
                FileInputStream fis = new FileInputStream(savePathItem);
                ZipEntry entry = new ZipEntry(savePathItem);
                zout.putNextEntry(entry);
                // считываем содержимое файла в массив byte
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // добавляем содержимое к архиву
                zout.write(buffer);
                // закрываем текущую запись для новой записи
                zout.closeEntry();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


}

