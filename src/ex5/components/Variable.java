package ex5.components;

/**
 * This class resembles a variable object.
 */
public class Variable {

    private final boolean isFinal;
    private final String type;
    private boolean isInitialized;

    /**
     * Constructor.
     * @param isFinal is a boolean that indicates if the variable is constant.
     * @param type is a String that indicates the type of the variable.
     * @param isInitialized is a boolean that indicates whether the variable is
     *                      initialized or not.
     */
    public Variable(boolean isFinal, String type, boolean isInitialized) {
        this.type = type;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
    }

    /**
     * Getter.
     * @return whether the variable is a constant.
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Getter.
     * @return the type of the variable.
     */
    public String getType() {
        return type;
    }

    /**
     * Getter.
     * @return whether the variable is initialized.
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Sets the variable to initialized state.
     */
    public void initialize() {
        isInitialized = true;
    }
}
