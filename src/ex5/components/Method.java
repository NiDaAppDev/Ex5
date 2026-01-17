package ex5.components;

import ex5.IllegalException;
import ex5.utils.MethodUtils;
import ex5.utils.NameVariablePair;

import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Method {
    private Scope scope;
    private Scope parentScope;
    private final String methodName;
    private final HashMap<String, Variable> parameters;
    private final List<String> paramTypes;
    private String[] methodBody;

    public Method(String[] groups, String[] codeLines, Scope parent) throws IllegalException {
        this.methodName = groups[1];
        this.methodBody = codeLines;
        this.parameters = new HashMap<>();
        this.paramTypes = new ArrayList<>();

        String paramString = groups[2];
        List<NameVariablePair> params = MethodUtils.extractMethodParameters(paramString);

        for (NameVariablePair param : params) {
            String name = param.getName();
            Variable var = param.getVariable();
            parameters.put(name, var);
            paramTypes.add(var.getType());
        }

        if(codeLines.length > 0) {
            this.scope = new Scope(parent,
                    codeLines,
                    parameters);
        }
        this.parentScope = parent;

        validateName();
    }

    public void call(String paramString) throws IllegalException {
        List<Variable> callArgs = MethodUtils.getArgsFromCall(paramString, parentScope);
        if (paramTypes.size() != callArgs.size()) {
            throw new IllegalException("Not enough arguments for a method call.");
        }

        for (int i = 0; i < paramTypes.size(); i++) {
            String expectedType = paramTypes.get(i);
            Variable actualArg = callArgs.get(i);
            if(!actualArg.isInitialized()) {
                throw new IllegalException("Used argument inside a method call isn't initialized.");
            }
            if(!isTypeCompatible(expectedType, actualArg.getType())) {
                throw new IllegalException("Used argument inside a method call isn't compatible with " +
                        "requested type.");
            }
        }
    }

    public String getMethodName() {
        return methodName;
    }

    private boolean isTypeCompatible(String expectedType, String actualType) {
        return expectedType.equals(actualType) ||
                (expectedType.equals(DOUBLE_TYPE) && actualType.equals(INT_TYPE)) ||
                (expectedType.equals(BOOLEAN_TYPE) && (actualType.equals(INT_TYPE) ||
                        actualType.equals(DOUBLE_TYPE)));
    }

    private void validateName() throws IllegalException {
        if(!scope.getMethods().isEmpty() &&
                scope.getMethods().containsKey(methodName)) {
            throw new IllegalException("Trying to create a method with an existing method name");
        }
    }

    public void parseCodeBlock() throws IllegalException {
        if(scope != null) {
            scope.parseCodeBlock();
        }
    }
}
