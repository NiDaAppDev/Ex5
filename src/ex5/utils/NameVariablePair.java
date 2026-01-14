package ex5.utils;

import ex5.components.Variable;

public class NameVariablePair {

    String name;
    Variable variable;

    NameVariablePair(String name, Variable variable) {
        this.name = name;
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }

    public String getName() {
        return name;
    }
}
