package ex5.utils;

import ex5.components.Variable;

/**
 * An object that holds the result of a variable assignment\declaration.
 */
public class NameVariablePair {

    private final String name;
    private final Variable variable;

    /**
     * Constructor.
     * @param name is the name of the variable.
     * @param variable is the extracted variable.
     */
    NameVariablePair(String name, Variable variable) {
        this.name = name;
        this.variable = variable;
    }

    /**
     * Getter.
     * @return the variable.
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * Getter.
     * @return the name of the found variable.
     */
    public String getName() {
        return name;
    }
}
