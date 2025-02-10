package main.java.ru.courses.streamApi.task2;


import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    private final String ipAdr;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent agent;

    public LogEntry(String logLine) throws Exception {
        int ipEnd = logLine.indexOf(' ');
        if (ipEnd < 0) {
            throw new Exception("Invalid log line format (IP)");
        }
        ipAdr = logLine.substring(0, ipEnd);

        int timeStart = logLine.indexOf('[');
        int timeEnd = logLine.indexOf(']');
        if (timeStart < 0 || timeEnd < 0) {
            throw new Exception("Invalid log line format (Date)");
        }
        String dateTimeStr = logLine.substring(timeStart + 1, timeEnd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        ZonedDateTime zdt = ZonedDateTime.parse(dateTimeStr, formatter);
        time = zdt.toLocalDateTime();

        int reqStart = logLine.indexOf('"', timeEnd);
        int reqEnd = logLine.indexOf('"', reqStart + 1);
        if (reqStart < 0 || reqEnd < 0) {
            throw new Exception("Invalid log line format (Request)");
        }
        String request = logLine.substring(reqStart + 1, reqEnd);
        String[] reqParts = request.split(" ");
        if (reqParts.length < 2) {
            throw new Exception("Invalid request format");
        }
        method = HttpMethod.fromString(reqParts[0]);
        path = reqParts[1];

        String afterRequest = logLine.substring(reqEnd + 1).trim();
        String[] partsAfterReq = afterRequest.split(" ");
        if (partsAfterReq.length < 2) {
            throw new Exception("Invalid log line format (code/size)");
        }
        responseCode = Integer.parseInt(partsAfterReq[0]);
        responseSize = Integer.parseInt(partsAfterReq[1]);

        int refStart = partsAfterReq[2].indexOf('"');
        int refEnd = partsAfterReq[2].indexOf('"', refStart + 1);
        if (refStart < 0 || refEnd < 0) {
            throw new Exception("Invalid log line format (referer)");
        }
        referer = partsAfterReq[2].substring(refStart + 1, refEnd);

        String userAgentStr = logLine.substring(refEnd + 2, logLine.length() - 1);
        agent = new UserAgent(userAgentStr);
    }

    public String getIpAdr() {
        return ipAdr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return agent;
    }
}
