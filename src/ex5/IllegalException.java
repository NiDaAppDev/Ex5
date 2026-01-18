package ex5;

/**
 * An exception indicating uncompilable line.
 */
public class IllegalException extends Exception {

    /**
     * Constructor.
     * @param message is an error message.
     */
    public IllegalException(String message) {
        super(message);
    }

}
