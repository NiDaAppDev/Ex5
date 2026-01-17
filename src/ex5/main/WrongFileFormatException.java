package ex5.main;

public class WrongFileFormatException extends RuntimeException {
    public WrongFileFormatException() {
        super("The inserted file is not an .sjava file.");
    }
}
