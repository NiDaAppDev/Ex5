package ex5.main;

/**
 * An illegal argument number exception object.
 */
public class IllegalArgumentNumberException extends RuntimeException {


    /**
     * Constructor.
     */
    public IllegalArgumentNumberException() {
        super("Too many or no arguments inserted. Please insert only one .sjava file path.");
    }

}
