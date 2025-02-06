import main.java.ru.courses.collections.task1.LogEntry;
import main.java.ru.courses.collections.task1.Statistics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Statistics stats = new Statistics();
        try (BufferedReader br = new BufferedReader(new FileReader("accesslog.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    LogEntry entry = new LogEntry(line);
                    stats.addEntry(entry);
                } catch (Exception e) {
                    System.err.println("Error parsing string: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Operating system shares:");
        for (Map.Entry<String, Double> entry : stats.getOsStatistics().entrySet()) {
            System.out.println(entry.getKey() + ": " + String.format("%.3f", entry.getValue()));
        }
    }
}
