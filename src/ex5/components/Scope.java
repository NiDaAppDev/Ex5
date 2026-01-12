package ex5.components;

import java.util.HashMap;

public class Scope {

    private final Scope parent;
    private final String codeBlock;

    private HashMap<String, Variable> variables;

    public Scope(Scope parent, String codeBlock) {
        this.parent = parent;
        this.codeBlock = codeBlock;
        variables = new HashMap<>();
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    public void assignVariable(String variableName, Variable variable) {
        variables.put(variableName, variable);
    }

}
