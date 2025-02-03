package main.java.ru.courses.oop.task1;

public class LineException extends RuntimeException{

    public LineException(){
        super();
    }
    public LineException(String message){
        super(message);
    }
    public LineException(String message, Throwable cause){
        super(message, cause);
    }
    public LineException(Throwable cause){
        super(cause);
    }
}
