package main.java.ru.courses.oop.task2;

import main.java.ru.courses.oop.task1.LineException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class BrowserBot {
    private final File logFile;

    public BrowserBot(File logFile) {
        if (!logFile.exists() || (!logFile.isFile())) {
            try {
                throw new FileNotFoundException("Log file does not exist or is not a file");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        this.logFile = logFile;
    }

    public void botShareSelection() {
        int totalRequests = 0;
        int googleBotCount = 0;
        int yandexBotCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalRequests++;
                int length = line.length();
                if (length > 1024) {
                    throw new LineException("The line is longer than 1024 characters");
                }

                String[] split = line.split("\"");
                if (split.length >= 6) {
                    String userAgent = split[5];

                    String[] openParts = userAgent.split("\\(");
                    if (openParts.length >= 2) {
                        String afterOpenPart = openParts[1];
                        String[] innerParts = afterOpenPart.split("\\)");
                        if (innerParts.length > 0) {
                            String firstInnerPart = innerParts[0];
                            String[] shareInnerParts = firstInnerPart.split(";");
                            for (int i = 0; i < shareInnerParts.length; i++) {
                                shareInnerParts[i] = shareInnerParts[i].trim();
                            }
                            if (shareInnerParts.length >= 2) {
                                String fragment = shareInnerParts[1];
                                String[] fragmentParts = fragment.split("/");
                                if (fragmentParts.length > 0) {
                                    String finalFragment = fragmentParts[0].trim();
                                    if (finalFragment.equals("Googlebot")) {
                                        googleBotCount++;
                                    } else if (finalFragment.equals("YandexBot")) {
                                        yandexBotCount++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        if (totalRequests > 0) {
            double googleFraction = (double) googleBotCount / totalRequests;
            double yandexFraction = (double) yandexBotCount / totalRequests;
            System.out.println("Share of queries from Googlebot: " + googleFraction);
            System.out.println("Share of queries from YandexBot: " + yandexFraction);
        } else {
            System.out.println("There are no lines to parse.");
        }
    }
}
