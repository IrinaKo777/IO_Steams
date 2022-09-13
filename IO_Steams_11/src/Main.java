import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class Main {
    private static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) {
        Date date = new Date();
        createNewDir("C://GamesNew/temp");
        createNewFile("C://GamesNew/temp/temp.txt");
        createNewDir("C://GamesNew/src");
        createNewDir("C://GamesNew/res");
        createNewDir("C://GamesNew/savegames");
        createNewDir("C://GamesNew/src/main");
        createNewDir("C://GamesNew/src/test");
        createNewDir("C://GamesNew/res/drawables");
        createNewDir("C://GamesNew/res/vectors");
        createNewDir("C://GamesNew/res/icons");
        createNewFile("C://GamesNew/src/main/Main.java");
        createNewFile("C://GamesNew/src/main/Utils.java");

        try (FileWriter log = new FileWriter("C://GamesNew/temp/temp.txt", true)) {
            log.write(String.valueOf(stringBuilder));
            log.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void createNewDir(String name) {
        File dir = new File(name);
        if (dir.mkdir()) {
            stringBuilder.append(new Date() + " - Каталог " + name + " создан" + "\n");

        } else {
            stringBuilder.append(new Date() + " - Каталог " + name + " не удалось создать" + "\n");
        }
    }

    public static void createNewFile(String name) {
        File file = new File(name);
        try {
            if (file.createNewFile()) {
                stringBuilder.append(new Date() + " - Файл " + name + " создан" + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}