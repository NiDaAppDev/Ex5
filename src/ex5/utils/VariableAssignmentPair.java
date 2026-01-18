package ex5.utils;

/**
 * This object holds the data parsed of a variable's assignment.
 */
public class VariableAssignmentPair {

    private final String name;
    private final String value;

    /**
     * @param name is the name of the assigned-to variable.
     * @param value is the value assigned to the variable.
     */
    public VariableAssignmentPair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Getter.
     * @return the name of the assigned-to variable.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter.
     * @return the value assigned to the variable.
     */
    public String getValue() {
        return value;
    }
}
