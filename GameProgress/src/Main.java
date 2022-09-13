import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gp1 = new GameProgress(8, 2, 15, 34);
        GameProgress gp2 = new GameProgress(10, 3, 18, 152);
        GameProgress gp3 = new GameProgress(12, 5, 20, 341);
        saveGame("C://GamesNew/savegames/save1.dat", gp1);
        saveGame("C://GamesNew/savegames/save2.dat", gp2);
        saveGame("C://GamesNew/savegames/save3.dat", gp3);
        List<String> list = new ArrayList<>();
        list.add("C://GamesNew/savegames/save1.dat");
        list.add("C://GamesNew/savegames/save2.dat");
        list.add("C://GamesNew/savegames/save3.dat");
        zipFiles("C://GamesNew/savegames/zip.zip", list);
        openZip("C://GamesNew/savegames/zip.zip", "C://GamesNew/savegames/");
        openProgress("C://GamesNew/savegames/save3.dat");
    }

    public static void saveGame (String path, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> list) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path));
             FileInputStream fis = new FileInputStream(path)) {
            for (String x : list) {
                ZipEntry entry = new ZipEntry(x);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
            }
            zout.closeEntry();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (String file : list) {
            File file1 = new File(file);
            if (file1.delete()) {
                System.out.println("Файл " + file + " удален");
            }
        }
    }

    public static void openZip(String path, String zipPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(path))) {
            ZipEntry entry;
            //String name;
            while ((entry = zin.getNextEntry()) != null) {
                zipPath = entry.getName();
                FileOutputStream fout = new FileOutputStream(zipPath);
                for (int c = zin.read(); c!= -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void openProgress(String path) {
        GameProgress gp = null;
        try (FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
                gp = (GameProgress) ois.readObject();
            } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(gp);
    }

}
