package main.java.ru.courses.oop.task3;

public class UserAgent {
    private final String osType;
    private final String browser;

    public UserAgent(String userAgentStr) {
        if (userAgentStr.contains("Windows")) {
            osType = "Windows";
        } else if (userAgentStr.contains("Mac OS") || userAgentStr.contains("Macintosh")) {
            osType = "macOS";
        } else if (userAgentStr.contains("Linux")) {
            osType = "Linux";
        } else {
            osType = "Unknown";
        }

        if (userAgentStr.contains("Edge")) {
            browser = "Edge";
        } else if (userAgentStr.contains("Firefox")) {
            browser = "Firefox";
        } else if (userAgentStr.contains("Chrome")) {
            browser = "Chrome";
        } else if (userAgentStr.contains("Opera") || userAgentStr.contains("OPR")) {
            browser = "Opera";
        } else {
            browser = "Other";
        }
    }

    public String getOsType() {
        return osType;
    }

    public String getBrowser() {
        return browser;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "osType='" + osType + '\'' +
                ", browser='" + browser + '\'' +
                '}';
    }
}
