package ex5.main;

/**
 * Exception of a wrong file format object.
 */
public class WrongFileFormatException extends RuntimeException {

    /**
     * Constructor.
     */
    public WrongFileFormatException() {
        super("The inserted file is not an .sjava file.");
    }

}
