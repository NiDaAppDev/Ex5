package ex5.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import ex5.utils.*;

import javax.sound.sampled.Line;

import static ex5.reg_ex_patterns.IfWhileRegExPatterns.SINGLE_CONDITION_PATTERN;

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

    private void parseCodeBlock() throws Exception{
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
                            VariableUtils.extractVariableAssignment(LineReader.getCurrentGroups()[1]);
                    if (!allVisibleVariables.containsKey(varAssignPair.getName())) {
                        //TODO: throw an "illegal" exception.
                    }
                    VariableUtils.assignValueToVariable(
                            allVisibleVariables.get(varAssignPair.getName()),
                            varAssignPair.getValue());
                }
                case IF_WHILE -> ifWhileStatements.add(
                        new IfWhile(
                                this,
                                LineReader.getCurrentGroups()[1],
                                extractSubScopeCodeBlock(i))
                );
                case METHOD_DEF -> {
                    if (parent != null) {
                        throw new Exception();
                    }
                    String[] body = extractSubScopeCodeBlock(i);
                    Method newMethod = new Method (LineReader.getCurrentGroups(), body, this);
                    methods.put(newMethod.getMethodName(), newMethod);
                    i += body.length - 1;
                }
                case METHOD_CALL -> {
                    if(!methods.containsKey(LineReader.getCurrentGroups()[1]) || parent == null) {
                        throw new Exception();
                    }

                    String methodName = LineReader.getCurrentGroups()[1];
                    String paramString = LineReader.getCurrentGroups()[2];

                    if (!getMethods().containsKey(methodName)) {
                        throw new Exception();
                    }

                    Method method = getMethods().get(methodName);

                    List<Variable> args = MethodUtils.getArgsFromCall(paramString, this);
                    method.call(args);
                }
                case ILLEGAL, default -> {//TODO: throw an "illegal" exception.}
                }
            }
        }
    }

    private String[] extractSubScopeCodeBlock(int currentIndex) {
        String[] remainingCode = SubScopeExtractor.getSubScopeCodeBlock(
                codeBlock,
                currentIndex + 1,
                codeBlock.length);
        int blockClosingBracketIndex = SubScopeExtractor.getBlockEndIndex(remainingCode);
        if (blockClosingBracketIndex == -1) {
            //TODO: throw an "illegal" exception.
        }
        return SubScopeExtractor.getSubScopeCodeBlock(
                codeBlock,
                currentIndex + 1,
                blockClosingBracketIndex);
    }
}
