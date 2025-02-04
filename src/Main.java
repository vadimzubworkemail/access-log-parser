import main.java.ru.courses.oop.task2.BrowserBot;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        BrowserBot userAgent = new BrowserBot(new File("accesslog.txt"));
        userAgent.botShareSelection();
    }
}
