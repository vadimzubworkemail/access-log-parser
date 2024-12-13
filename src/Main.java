import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numValidPaths = 0;

        while (true) {
            System.out.println("Введите путь к файлу и нажмите Enter: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (!fileExists) {
                System.out.println("Указанный файл не найден, укажите верый путь");
                continue;
            } else if (isDirectory) {
                System.out.println("Указанный путь ведет к папке, укажите путь к файлу");
                continue;
            }
            numValidPaths++;
            System.out.println("Путь указан верно! Это файл номер N " + numValidPaths);
        }
    }
}
