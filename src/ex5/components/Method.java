package ex5.components;

import ex5.utils.MethodUtils;
import ex5.utils.NameVariablePair;
import ex5.utils.VariableUtils;
import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Method {
    private Scope scope;
    private final String methodName;
    private final HashMap<String, Variable> parameters;
    private final List<String> paramTypes;
    private String[] methodBody;
    private Scope parent;

    public Method(String[] groups, String[] codeLines, Scope parent) throws Exception {
        this.methodName = groups[1];
        this.parent = parent;
        this.methodBody = codeLines;
        this.parameters = new HashMap<>();
        this.paramTypes = new ArrayList<>();

        validateName();

        String paramString = groups[2];
        List<NameVariablePair> params = MethodUtils.extractMethodParameters(paramString);

        for (NameVariablePair param : params) {
            String name = param.getName();
            Variable var = param.getVariable();
            parameters.put(name, var);
            paramTypes.add(var.getType());
        }
    }

    public void validate() throws Exception {
        this.scope = new Scope(parent, methodBody,new HashMap<>(parameters));
        this.scope.parseCodeBlock();
    }

    public void call(List<Variable> callArgs) throws Exception{
        if (paramTypes.size() != callArgs.size()) {
            throw new Exception();
        }

        for (int i = 0; i < paramTypes.size(); i++) {
            String expectedType = paramTypes.get(i);
            Variable actualArg = callArgs.get(i);
            if(!actualArg.isInitialized()) {
                throw new Exception();
            }
            if(!isTypeCompatible(expectedType, actualArg.getType())) {
                throw new Exception();
            }
        }
    }

    public String getMethodName() {
        return methodName;
    }

    private boolean isTypeCompatible(String expectedType, String actualType) {
        return expectedType.equals(actualType) ||
                (expectedType.equals(DOUBLE_TYPE) && actualType.equals(INT_TYPE)) ||
                (expectedType.equals(BOOLEAN_TYPE) && (actualType.equals(INT_TYPE) || actualType.equals(DOUBLE_TYPE)));
    }

    private void validateName() throws Exception {
        if(parent.getMethods().containsKey(this.methodName)) {
            throw new Exception();
        }
    }
}
