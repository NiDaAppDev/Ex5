package ex5.components;

import java.util.HashMap;

public class Scope {

    private final Scope parent;

    private HashMap<String, Variable> variables;

    public Scope(Scope parent) {
        this.parent = parent;
        variables = new HashMap<>();
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    public void assignVariable(String variableName, Variable variable) {
        variables.put(variableName, variable);
    }

}
