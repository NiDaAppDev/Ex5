package ex5.components;

import java.util.ArrayList;
import java.util.HashMap;

import ex5.IllegalException;
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

    public Scope(Scope parent,
                 String[] codeBlock) {
        this.parent = parent;
        this.codeBlock = codeBlock;
        this.variables = new  HashMap<>();
        allVisibleVariables = getAllVisibleVariables();
        methods = new HashMap<>();
        ifWhileStatements = new ArrayList<>();
    }

    public Scope(String[] codeBlock) {
        this.parent = null;
        this.codeBlock = codeBlock;
        this.variables = new  HashMap<>();
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

    public HashMap<String, Method> getMethods() {
        if(parent==null) return methods;
        return parent.getMethods();
    }

    public HashMap<String, Variable> getAllVisibleVariables() {
        if (parent == null) return variables;
        HashMap<String, Variable> allVariables = parent.getAllVisibleVariables();
        allVariables.putAll(variables);
        return allVariables;
    }

    public void parseCodeBlock() throws IllegalException {
        for (int i = 0; i < codeBlock.length; i++) {
            LineAnalysis currentLineAnalysis = LineReader.classifyLine(codeBlock[i]);
            LineReader.LINE_TYPE currentLineType = currentLineAnalysis.getType();
            String[] currentCapturedGroups = currentLineAnalysis.getGroups();
            switch (currentLineType) {
                case IGNORE -> {
                }
                case VAR_DEF -> {
                    for (NameVariablePair varPair :
                            VariableUtils.extractDeclaredVariables(getAllVisibleVariables(),
                                    currentCapturedGroups)) {
                        if (variables.containsKey(varPair.getName())) {
                            throw new IllegalException("Trying to declare a variable with a name" +
                                    " of a variable that already exists in his context.");
                        }
                        addVariable(varPair.getName(), varPair.getVariable());
                    }
                }
                case VAR_ASSIGN -> {
                    VariableAssignmentPair varAssignPair =
                            VariableUtils.extractVariableAssignment(currentCapturedGroups[1]);
                    if (!allVisibleVariables.containsKey(varAssignPair.getName())) {
                        throw new IllegalException("Trying to assign to a variable that doesn't exist" +
                                " in this context.");
                    }
                    VariableUtils.assignValueToVariable(
                            getAllVisibleVariables(),
                            allVisibleVariables.get(varAssignPair.getName()),
                            varAssignPair.getValue());
                }
                case IF_WHILE -> {
                    String[] body = extractSubScopeCodeBlock(i);
                    ifWhileStatements.add(
                            new IfWhile(
                                    this,
                                    currentCapturedGroups[1],
                                    body)
                    );
                    i += body.length + 2;
                }
                case METHOD_DEF -> {
                    if (parent != null) {
                        throw new IllegalException("Trying to create a method in a scope that's not" +
                                " the global scope.");
                    }
                    String[] body = extractSubScopeCodeBlock(i);
                    Method newMethod = new Method(
                            currentCapturedGroups,
                            body,
                            this);
                    methods.put(newMethod.getMethodName(), newMethod);
                    i += body.length + 2;
                }
                case METHOD_CALL -> {
                    if(!methods.containsKey(currentCapturedGroups[1]) || parent == null) {
                        throw new IllegalException("Calling to a method that doesn't exist, or " +
                                "trying to call a method from the global scope.");
                    }

                    String methodName = currentCapturedGroups[1];
                    String paramString = currentCapturedGroups[2];

                    if (!getMethods().containsKey(methodName)) {
                        throw new IllegalException("Calling to a method that doesn't exist.");
                    }

                    getMethods().get(methodName).call(paramString);
                }
                default -> throw new IllegalException("Couldn't analyze a line - not an sJava code line.");
            }
        }
        parseSubScopesCodeBlocks();
    }

    private void parseSubScopesCodeBlocks() throws IllegalException {
        for (IfWhile ifWhile : ifWhileStatements) {
            ifWhile.parseCodeBlock();
        }
        for(Method method : methods.values()) {
            method.parseCodeBlock();
        }
    }

    private String[] extractSubScopeCodeBlock(int currentIndex) throws IllegalException{
        String[] remainingCode = SubScopeExtractor.getSubScopeCodeBlock(
                codeBlock,
                currentIndex + 1,
                codeBlock.length);
        int blockClosingBracketIndex = SubScopeExtractor.getBlockEndIndex(remainingCode) + 1;
        if (blockClosingBracketIndex == -1) {
            throw new IllegalException("Couldn't find block closing bracket.");
        }
        if(blockClosingBracketIndex == 1) {
            return new String[0];
        }
        return SubScopeExtractor.getSubScopeCodeBlock(
                codeBlock,
                currentIndex + 1,
                blockClosingBracketIndex);
    }
}
