package ex5.main;

public class IllegalArgumentNumberException extends RuntimeException {
    public IllegalArgumentNumberException() {
        super("Too many or no arguments inserted. Please insert only one .sjava file path.");
    }
}
