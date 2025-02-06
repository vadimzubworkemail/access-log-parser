package main.java.ru.courses.collections.task2;

enum HttpMethod {
    GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, CONNECT;

    public static HttpMethod fromString(String method) {
        try {
            return HttpMethod.valueOf(method);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}

