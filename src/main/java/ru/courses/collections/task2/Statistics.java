package main.java.ru.courses.collections.task2;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private final Set<String> existingPages = new HashSet<>();
    private final Set<String> nonExistingPages = new HashSet<>();
    private final Map<String, Integer> osCounts = new HashMap<>();
    private final Map<String, Integer> browserCounts = new HashMap<>();
    private int totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;
    private int totalEntries = 0;

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getResponseSize();
        totalEntries++;

        if (minTime == null || entry.getTime().isBefore(minTime)) {
            minTime = entry.getTime();
        }
        if (maxTime == null || entry.getTime().isAfter(maxTime)) {
            maxTime = entry.getTime();
        }

        if (entry.getResponseCode() == 200) {
            existingPages.add(entry.getPath());
        } else if (entry.getResponseCode() == 404) {
            nonExistingPages.add(entry.getPath());
        }

        String os = entry.getUserAgent().getOs();
        osCounts.put(os, osCounts.getOrDefault(os, 0) + 1);

        String browser = entry.getUserAgent().getBrowser();
        browserCounts.put(browser, browserCounts.getOrDefault(browser, 0) + 1);
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null || minTime.equals(maxTime)) {
            return 0;
        }
        long hours = java.time.Duration.between(minTime, maxTime).toHours();
        return (hours > 0) ? (double) totalTraffic / hours : totalTraffic;
    }

    public Set<String> getExistingPages() {
        return existingPages;
    }

    public Set<String> getNonExistingPages() {
        return nonExistingPages;
    }

    public Map<String, Double> getOsStatistics() {
        Map<String, Double> osStats = new HashMap<>();
        for (Map.Entry<String, Integer> entry : osCounts.entrySet()) {
            osStats.put(entry.getKey(), (double) entry.getValue() / totalEntries);
        }
        return osStats;
    }

    public Map<String, Double> getBrowserStatistics() {
        Map<String, Double> browserStats = new HashMap<>();
        for (Map.Entry<String, Integer> entry : browserCounts.entrySet()) {
            browserStats.put(entry.getKey(), (double) entry.getValue() / totalEntries);
        }
        return browserStats;
    }
}

