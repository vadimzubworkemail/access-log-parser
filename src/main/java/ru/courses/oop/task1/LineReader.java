package main.java.ru.courses.oop.task1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LineReader {

    public static void main(String[] args) {
        File file = new File("access.log");

        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }
        if (!file.isFile()) {
            System.out.println("File is not a file");
        }

        int lineCount = 0;
        int maxLength = 0;
        int minLength = Integer.MAX_VALUE;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                int length = line.length();
                if (length > 1024) {
                    throw new LineException("The line is longer than 1024 characters");
                }
                if (length > maxLength) {
                    maxLength = length;
                }
                if (length < minLength) {
                    minLength = length;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (lineCount == 0) {
            minLength = 0;
        }
        System.out.println("Total number of lines: " + lineCount);
        System.out.println("Maximum line length: " + maxLength);
        System.out.println("Minimum line length: " + minLength);
    }
}
