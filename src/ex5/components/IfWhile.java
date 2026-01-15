package ex5.components;

import ex5.utils.IfWhileUtils;
import ex5.utils.LineReader;

import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.IfWhileRegExPatterns.SINGLE_CONDITION_PATTERN;
import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

public class IfWhile {

    private final Scope scope;
    private final Scope parentScope;

    public IfWhile(Scope parentScope, String conditionBlock, String[] codeLines) throws Exception {
        this.parentScope = parentScope;
        validateConditionBlock(conditionBlock);
        this.scope = new Scope(parentScope, codeLines);
        this.scope.parseCodeBlock();
    }

    private void validateConditionBlock(String conditionBlock) throws Exception {
        for(String varName : IfWhileUtils.
                extractVarNamesInCondition(conditionBlock)) {
            if(!parentScope.getAllVisibleVariables().containsKey(varName)) {
                throw new Exception();
            }
            Variable var = parentScope.getAllVisibleVariables().get(varName);

            if (!var.isInitialized()) {
                throw new Exception();
            }
            String type = var.getType();
            if (!type.equals(BOOLEAN_TYPE)
                    && !type.equals(INT_TYPE)
                    && !type.equals(DOUBLE_TYPE)) {
                throw new Exception();
            }
        }
    }

    public Scope getScope() {
        return scope;
    }

}
