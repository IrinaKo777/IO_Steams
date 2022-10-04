import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    private static StringBuilder stringBuilder = new StringBuilder();
    private static String[] dirName = {"C://GamesNew/temp", "C://GamesNew/src", "C://GamesNew/res",
            "C://GamesNew/savegames", "C://GamesNew/src/main", "C://GamesNew/src/test",
            "C://GamesNew/res/drawables", "C://GamesNew/res/vectors", "C://GamesNew/res/icons"
    };

    private static String[] fileName = {"C://GamesNew/temp/temp.txt", "C://GamesNew/src/main/Main.java", "C://GamesNew/src/main/Utils.java"};

    public static void main(String[] args) {
        createNewDir(dirName);
        createNewFile(fileName);

//        try (FileWriter log = new FileWriter("C://GamesNew/temp/temp.txt", true)) {
//            log.write(String.valueOf(stringBuilder));
//            log.flush();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }

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
        openZip("C://GamesNew/savegames/zip.zip");
        openProgress("C://GamesNew/savegames/save3.dat");

    }

    public static void logger(){
        try (FileWriter log = new FileWriter("C://GamesNew/temp/temp.txt", true)) {
            log.write(String.valueOf(stringBuilder));
            log.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
             ) {
            for (String x : list) {
                FileInputStream fis = new FileInputStream(x);
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
                stringBuilder.append(new Date()).append(" - Файл ").append(file).append(" удален \n");
                logger();
            }
        }
    }

    public static void openZip(String path) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(path))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
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

    public static void createNewDir(String[] mass) {
        for (int i = 0; i < mass.length; i++) {
            File dir = new File(mass[i]);
            stringBuilder.append(new Date());
            if (dir.mkdir()) {
                stringBuilder.append(" - Каталог ").append(mass[i]).append(" создан \n");

            } else {
                stringBuilder.append(" - Каталог ").append(mass[i]).append(" не создан! \n");
            }
        }
        logger();
    }

    public static void createNewFile(String[] mass) {
        for (int i = 0; i < mass.length; i++) {
            File file = new File(mass[i]);
            try {
                if (file.createNewFile()) {
                    stringBuilder.append(new Date()).append(" - Файл ").append(mass[i]).append(" создан \n");;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        logger();
    }

}
