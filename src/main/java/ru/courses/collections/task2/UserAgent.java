package main.java.ru.courses.collections.task2;

public class UserAgent {
    private final String os;
    private final String browser;

    public UserAgent(String userAgentString) {
        this.os = detectOS(userAgentString);
        this.browser = detectBrowser(userAgentString);
    }

    private String detectOS(String userAgent) {
        if (userAgent.contains("Windows")) return "Windows";
        if (userAgent.contains("Mac")) return "macOS";
        if (userAgent.contains("Linux")) return "Linux";
        return "Other";
    }

    private String detectBrowser(String userAgent) {
        if (userAgent.contains("Chrome")) return "Chrome";
        if (userAgent.contains("Firefox")) return "Firefox";
        if (userAgent.contains("Opera")) return "Opera";
        if (userAgent.contains("Edge")) return "Edge";
        return "Other";
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }
}

