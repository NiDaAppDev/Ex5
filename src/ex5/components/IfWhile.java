package ex5.components;

import ex5.utils.IfWhileUtils;
import ex5.utils.LineReader;

import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.IfWhileRegExPatterns.SINGLE_CONDITION_PATTERN;

public class IfWhile {

    private final Scope scope;
    private final Scope parentScope;

    public IfWhile(Scope parentScope, String conditionBlock, String[] codeLines) {
        this.parentScope = parentScope;
        validateConditionBlock(conditionBlock);
        this.scope = new Scope(parentScope, codeLines);
    }

    private void validateConditionBlock(String conditionBlock) {
        for(String varName : IfWhileUtils.
                extractVarNamesInCondition(LineReader.getCurrentGroups()[1])) {
            if(!parentScope.getAllVisibleVariables().containsKey(varName) ||
                    !parentScope.getAllVisibleVariables().get(varName).isInitialized()) {
                //TODO: throw an "illegal" exception.
            }
            Matcher conditionalVarM = SINGLE_CONDITION_PATTERN.matcher(
                    parentScope.getAllVisibleVariables().get(varName).getType());
            if(!conditionalVarM.matches()) {
                //TODO: throw an "illegal" exception.
            }
        }
    }

    public Scope getScope() {
        return scope;
    }

}
