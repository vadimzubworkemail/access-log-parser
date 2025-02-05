import main.java.ru.courses.oop.task3.LogEntry;
import main.java.ru.courses.oop.task3.Statistics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Statistics stats = new Statistics();
        try (BufferedReader br = new BufferedReader(new FileReader("accesslog.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    LogEntry entry = new LogEntry(line);
                    stats.addEntry(entry);
                    System.out.println("IP: " + entry.getIpAdr() +
                            ", Time: " + entry.getTime() +
                            ", Method: " + entry.getMethod() +
                            ", Path: " + entry.getPath() +
                            ", Code: " + entry.getResponseCode() +
                            ", Size: " + entry.getResponseSize() +
                            ", Referer: " + entry.getReferer() +
                            ", OS: " + entry.getAgent().getOsType() +
                            ", Browser: " + entry.getAgent().getBrowser());
                } catch (Exception e) {
                    System.err.println("Error parsing string: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Total traffic volume: " + stats.getTotalTraffic());
        System.out.println("Start: " + stats.getMinTime());
        System.out.println("End: " + stats.getMaxTime());
        System.out.println("Traffic per hour: " + stats.getTrafficRate());
    }
}
