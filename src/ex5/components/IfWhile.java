package ex5.components;

import ex5.IllegalException;
import ex5.utils.IfWhileUtils;

import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.IfWhileRegExPatterns.SINGLE_CONDITION_PATTERN;

public class IfWhile {

    private Scope scope;
    private final Scope parentScope;

    public IfWhile(Scope parentScope, String conditionBlock, String[] codeLines) throws IllegalException {
        this.parentScope = parentScope;
        validateConditionBlock(conditionBlock);
        if(codeLines.length > 0) {
            this.scope = new Scope(parentScope, codeLines);
        }
    }

    private void validateConditionBlock(String conditionBlock) throws IllegalException {
        for(String varName : IfWhileUtils.
                extractVarNamesInCondition(conditionBlock)) {
            if(!parentScope.getAllVisibleVariables().containsKey(varName) ||
                    !parentScope.getAllVisibleVariables().get(varName).isInitialized()) {
                throw new IllegalException("Used variable doesn't exist or isn't initialized.");
            }
            Matcher conditionalVarM = SINGLE_CONDITION_PATTERN.matcher(
                    parentScope.getAllVisibleVariables().get(varName).getType());
            if(!conditionalVarM.matches()) {
                throw new IllegalException("Used variable has incompatible type (non-boolean inside" +
                        "a condition)");
            }
        }
    }

    public void parseCodeBlock() throws IllegalException {
        if(scope != null) {
            scope.parseCodeBlock();
        }
    }

}
