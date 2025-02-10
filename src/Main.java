import main.java.ru.courses.streamApi.task1.LogEntry;
import main.java.ru.courses.streamApi.task1.Statistics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Statistics statistics = getStatistics();
        System.out.println("Average number of site visits per hour: " + String.format("%2f", statistics.getAverageVisitsPerHour()));
        System.out.println("Average number of erroneous requests per hour: " + String.format("%2f", statistics.getAverageErrorsPerHour()));
        System.out.println("Average number of visits per user per hour: " + String.format("%2f", statistics.getAverageVisitsPerUser()));
    }

    private static Statistics getStatistics() {
        Statistics statistics = new Statistics();
        try (BufferedReader br = new BufferedReader(new FileReader("accesslog.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogEntry logEntry;
                try {
                    logEntry = new LogEntry(line);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                statistics.addEntry(logEntry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return statistics;
    }
}
