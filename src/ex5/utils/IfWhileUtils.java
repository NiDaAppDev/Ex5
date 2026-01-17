package ex5.utils;

import ex5.IllegalException;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.VariableRegExPatterns.VALID_VARIABLE_CALL;

import static ex5.reg_ex_patterns.IfWhileRegExPatterns.CONDITION_BLOCK_PATTERN;

public class IfWhileUtils {

    public static ArrayList<String> extractVarNamesInCondition(String conditionBlock)
            throws IllegalException {
        ArrayList<String> varNames = new ArrayList<>();
        String[] conditions =  extractConditionsFromBlock(conditionBlock);
        for(String condition : conditions) {
            Matcher varNameM = VALID_VARIABLE_CALL.matcher(condition);
            if(varNameM.matches()) {
                varNames.add(varNameM.group(1));
            }
        }
        return varNames;
    }

    private static String[] extractConditionsFromBlock(String conditionBlock) throws IllegalException {
        Matcher conditionMatcher = CONDITION_BLOCK_PATTERN.matcher(conditionBlock);
        if(!conditionMatcher.matches()) {
            throw new IllegalException("Tried to find a condition, but couldn't.");
        }
        String[] conditions =  new String[conditionMatcher.groupCount() - 1];
        for(int i = 0; i < conditions.length; i++) {
            conditions[i] = conditionMatcher.group(i + 1);
        }
        return conditions;
    }

}
