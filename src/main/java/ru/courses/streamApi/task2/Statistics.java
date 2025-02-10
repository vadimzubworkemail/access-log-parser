package main.java.ru.courses.streamApi.task2;


import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private int totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;
    private final Set<String> existingPages = new HashSet<>();
    private final Set<String> nonExistingPages = new HashSet<>();
    private final Map<String, Integer> osCounts = new HashMap<>();
    private final Map<String, Integer> browserCounts = new HashMap<>();
    private final Map<Integer, Integer> visitPerSecond = new HashMap<>();
    private final Map<String, Integer> userVisits = new HashMap<>();
    private final Set<String> referers = new HashSet<>();
    private int totalEntries = 0;
    private int totalVisits = 0;
    private int errorRequests = 0;
    private final Set<String> uniqueRealUsers = new HashSet<>();

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

        if (!entry.getUserAgent().isBot()) {
            totalVisits++;
            uniqueRealUsers.add(entry.getIpAdr());
        }

        if (entry.getResponseCode() >= 400) {
            errorRequests++;
        }

        int second = entry.getTime().getSecond();
        visitPerSecond.put(second, visitPerSecond.getOrDefault(second, 0) + 1);

        if (!entry.getUserAgent().getBrowser().toLowerCase().contains("bot")) {
            userVisits.put(entry.getIpAdr(), userVisits.getOrDefault(entry.getIpAdr(), 0) + 1);
        }

        if (!entry.getReferer().equals("-")) {
            referers.add(getRefererFromUrl(entry.getReferer()));
        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null || minTime.equals(maxTime)) {
            return 0;
        }
        long hours = Duration.between(minTime, maxTime).toHours();
        return (hours > 0) ? (double) totalTraffic / hours : totalTraffic;
    }

    public double getAverageVisitsPerHour() {
        long hours = Duration.between(minTime, maxTime).toHours();
        return (hours > 0) ? (double) totalVisits / hours : totalVisits;
    }

    public double getAverageErrorsPerHour() {
        long hours = Duration.between(minTime, maxTime).toHours();
        return (hours > 0) ? (double) errorRequests / hours : errorRequests;
    }

    public double getAverageVisitsPerUser() {
        return uniqueRealUsers.isEmpty() ? 0 : (double) totalVisits / uniqueRealUsers.size();
    }

    public Set<String> getExistingPages() {
        return new HashSet<>(existingPages); }

    public Set<String> getNonExistingPages() { return nonExistingPages; }

    private Map<String, Double> calculateStatistics(Map<String, Integer> counts) {
        Map<String, Double> statistics = new HashMap<>();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            statistics.put(entry.getKey(), (double) entry.getValue() / totalEntries);
        }
        return statistics;
    }

    public Map<String, Double> getOsStatistics() {
        return calculateStatistics(osCounts);
    }

    public Map<String, Double> getBrowserStatistics() {
        return calculateStatistics(browserCounts);
    }

    public int getPeakVisitsPerSecond() {
        return visitPerSecond.values().stream().max(Integer::compareTo).orElse(0);
    }

    public Set<String> getReferers() {
        return new HashSet<>(referers);
    }

    private String getRefererFromUrl(String url) {
        try {
            return new URL(url).getHost();
        } catch (Exception e) {
            return url;
        }
    }

    public int getMaxVisitsByOneUser() {
        return userVisits.values().stream().max(Integer::compareTo).orElse(0);
    }
}
