package main.java.ru.courses.streamApi.task2;

public enum HttpMethod {
    GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, CONNECT;

    public static HttpMethod fromString(String method) {
        try {
            return HttpMethod.valueOf(method);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
