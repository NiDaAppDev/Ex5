package ex5.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import ex5.utils.*;

import javax.sound.sampled.Line;

import static ex5.reg_ex_patterns.IfWhileRegExPatterns.SINGLE_CONDITION_PATTERN;
import static ex5.reg_ex_patterns.MethodRegExPatterns.RETURN_STATEMENT;

public class Scope {

    private final Scope parent;
    private final String[] codeBlock;

    private HashMap<String, Variable> variables;
    private HashMap<String, Method> methods;
    private ArrayList<IfWhile> ifWhileStatements;

    public Scope(Scope parent,
                 String[] codeBlock,
                 HashMap<String, Variable> variables) {
        this.parent = parent;
        this.codeBlock = codeBlock;
        this.variables = variables;
        this.methods = new HashMap<>();
        ifWhileStatements = new ArrayList<>();
    }

    public Scope(Scope parent,
                 String[] codeBlock) {
        this.parent = parent;
        this.codeBlock = codeBlock;
        this.variables = new  HashMap<>();
        methods = new HashMap<>();
        ifWhileStatements = new ArrayList<>();
    }

    public Scope(String[] codeBlock) {
        this.parent = null;
        this.codeBlock = codeBlock;
        this.variables = new  HashMap<>();
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
        HashMap<String, Variable> allVars = new HashMap<>();
        if (parent != null) {
            allVars.putAll(parent.getAllVisibleVariables());
        }
        allVars.putAll(variables);
        return allVars;
    }

    public void parseCodeBlock() throws Exception {

        for (int i = 0; i < codeBlock.length; i++) {
            LineReader.LINE_TYPE currentLineType = LineReader.classifyLine(codeBlock[i]);

            if (currentLineType == LineReader.LINE_TYPE.IGNORE) {
                continue;
            }
            else if (currentLineType == LineReader.LINE_TYPE.VAR_DEF) {
                for (NameVariablePair varPair : VariableUtils.extractDeclaredVariables(LineReader.getCurrentGroups(), this)) {
                    if (variables.containsKey(varPair.getName())) {
                        throw new Exception();
                    }
                    addVariable(varPair.getName(), varPair.getVariable());
                }
            }
            else if (currentLineType == LineReader.LINE_TYPE.VAR_ASSIGN) {
                VariableAssignmentPair varAssignPair = VariableUtils.extractVariableAssignment(LineReader.getCurrentGroups()[1]);
                if (!getAllVisibleVariables().containsKey(varAssignPair.getName())) {
                    throw new Exception();
                }
                Variable var = getAllVisibleVariables().get(varAssignPair.getName());

                if (var == null || var.isFinal()) throw new Exception();

                VariableUtils.validateAndAssign(
                        var,
                        varAssignPair.getValue(), this);
            }
            else if (currentLineType == LineReader.LINE_TYPE.IF_WHILE) {
                if (parent == null) throw new Exception();

                String condition = LineReader.getCurrentGroups()[1];
                String[] body = extractSubScopeCodeBlock(i);
                ifWhileStatements.add(new IfWhile(this, condition, body));
                i += body.length + 1;
            }
            else if (currentLineType == LineReader.LINE_TYPE.METHOD_DEF) {
                if (parent != null) throw new Exception();
                String[] methodGroups = LineReader.getCurrentGroups();
                String[] body = extractSubScopeCodeBlock(i);

                boolean hasReturn = false;
                for (String line : body) {
                    if (line.trim().matches(RETURN_STATEMENT)) {
                        hasReturn = true;
                        break;
                    }
                }
                if (!hasReturn) throw new Exception();

                Method newMethod = new Method(methodGroups, body, this);
                methods.put(newMethod.getMethodName(), newMethod);

                i += body.length + 1;
            }
            else if (currentLineType == LineReader.LINE_TYPE.METHOD_CALL) {
                if (parent == null) throw new Exception();

                String methodName = LineReader.getCurrentGroups()[1];
                String paramString = LineReader.getCurrentGroups()[2];

                if (!getMethods().containsKey(methodName)) throw new Exception();

                Method method = getMethods().get(methodName);
                List<Variable> args = MethodUtils.getArgsFromCall(paramString, this);
                method.call(args);
            }
            else if (currentLineType == LineReader.LINE_TYPE.RETURN) {
            }
            else {
                throw new Exception();
            }
        }

        if (parent == null) {
            for (Method m : methods.values()) {
                m.validate();
            }
        }
    }

    private String[] extractSubScopeCodeBlock(int currentIndex) throws Exception{
        String[] remainingCode = SubScopeExtractor.getSubScopeCodeBlock(
                codeBlock,
                currentIndex + 1,
                codeBlock.length);
        int blockClosingBracketIndex = SubScopeExtractor.getBlockEndIndex(remainingCode);
        if (blockClosingBracketIndex == -1) {
            throw new Exception();
        }
        int actualEndIndex = (currentIndex + 1) + blockClosingBracketIndex;
        return SubScopeExtractor.getSubScopeCodeBlock(
                codeBlock,
                currentIndex + 1,
                actualEndIndex);
    }
}
