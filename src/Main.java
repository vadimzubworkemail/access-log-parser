import main.java.ru.courses.streamApi.task2.LogEntry;
import main.java.ru.courses.streamApi.task2.Statistics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Statistics statistics = getStatistics();

        System.out.println("Peak website traffic per second: " + statistics.getPeakVisitsPerSecond());
        System.out.println("Maximum website traffic by one user: " + statistics.getMaxVisitsByOneUser());
        System.out.println("List of sites with links to the current site: " + statistics.getReferers());
    }

    private static Statistics getStatistics() {
        Statistics statistics = new Statistics();
        try (BufferedReader br = new BufferedReader(new FileReader("accesslog.txt"))) {
            String line;
            LogEntry entry;
            while ((line = br.readLine()) != null) {
                try {
                    entry = new LogEntry(line);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                statistics.addEntry(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return statistics;
    }
}
