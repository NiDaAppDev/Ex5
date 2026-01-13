package ex5.components;

import java.util.HashMap;

import static ex5.utils.LineReader.*;

public class Scope {

    private final Scope parent;
    private final String[] codeBlock;

    private HashMap<String, Variable> variables;
    private HashMap<String, Method> methods;
    private IfWhileBlocks[] ifStatements;

    public Scope(Scope parent,
                 String[] codeBlock,
                 HashMap<String, Variable> variables) {
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

    private void parseCodeBlock() {

        for(String codeLine : codeBlock) {

            LINE_TYPE currentLineType = classifyLine(codeLine);
            switch (currentLineType) {
//                case VAR_DEF ->
            }

        }

    }

}
