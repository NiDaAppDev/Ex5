package ex5.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ex5.utils.*;

public class Scope {

    private final Scope parent;
    private final String[] codeBlock;

    private HashMap<String, Variable> variables;
    private HashMap<String, Variable> allVisibleVariables;
    private HashMap<String, Method> methods;
    private ArrayList<IfWhile> ifWhileStatements;

    public Scope(Scope parent,
                 String[] codeBlock,
                 HashMap<String, Variable> variables) {
        this.parent = parent;
        this.codeBlock = codeBlock;
        this.variables = variables;
        allVisibleVariables = getAllVisibleVariables();
        methods = new HashMap<>();
        ifWhileStatements = new ArrayList<>();
    }

    public void addVariable(String variableName, Variable variable) {
        variables.put(variableName, variable);
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    public HashMap<String, Variable> getAllVisibleVariables() {
        if (parent == null) return variables;
        HashMap<String, Variable> allVariables = parent.getAllVisibleVariables();
        allVariables.putAll(variables);
        return allVariables;
    }

    private void parseCodeBlock() {

        for (int i = 0; i < codeBlock.length; i++) {
            LineReader.LINE_TYPE currentLineType = LineReader.classifyLine(codeBlock[i]);
            switch (currentLineType) {
                case IGNORE -> {
                }
                case VAR_DEF -> {
                    for (NameVariablePair varPair :
                            VariableUtils.extractDeclaredVariables(LineReader.getCurrentGroups())) {
                        if (variables.containsKey(varPair.getName())) {
                            //TODO: throw an "illegal" exception.
                        }
                        addVariable(varPair.getName(), varPair.getVariable());
                    }
                }
                case VAR_ASSIGN -> {
                    VariableAssignmentPair varAssignPair =
                            VariableUtils.extractVariableAssignment(LineReader.getCurrentGroups()[0]);
                    if (!allVisibleVariables.containsKey(varAssignPair.getName())) {
                        //TODO: throw an "illegal" exception.
                    }
                    VariableUtils.assignValueToVariable(
                            allVisibleVariables.get(varAssignPair.getName()),
                            varAssignPair.getValue());
                }
                case IF_WHILE -> {
                    String[] remainingCode = Arrays.copyOfRange(codeBlock, i + 1, codeBlock.length);
                    int blockClosingBracketIndex = SubScopeExtractor.getBlockEndIndex(remainingCode);
                    if (blockClosingBracketIndex == -1) {
                        //TODO: throw an "illegal" exception.
                    }
                    String[] subScopeCodeBlock = Arrays.copyOfRange(codeBlock,
                            i + 1,
                            blockClosingBracketIndex);
                    IfWhile ifWhile = new IfWhile(LineReader.getCurrentGroups()[0], subScopeCodeBlock);
                }
                case METHOD_DEF -> {

                }
                case METHOD_CALL -> {
                    if(!methods.containsKey(LineReader.getCurrentGroups()[0])) {
                        //TODO: throw an "illegal" exception.
                    }
//                    methods.
//                            get(LineReader.getCurrentGroups()[0]).
//                            call(LineReader.getCurrentGroups()[1]);
                }
                case ILLEGAL, default -> {//TODO: throw an "illegal" exception.}
                }
            }

        }

    }
}
