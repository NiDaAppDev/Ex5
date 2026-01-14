package ex5.utils;

import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.VariableRegExPatterns.VALID_VARIABLE_CALL;

import static ex5.reg_ex_patterns.IfWhileRegExPatterns.CONDITION_BLOCK_PATTERN;

public class IfWhileUtils {

    public static String[] extractVarNamesInCondition(String conditionBlock) {
        Matcher conditionMatcher = CONDITION_BLOCK_PATTERN.matcher(conditionBlock);
        String[] conditions =  new String[conditionMatcher.groupCount()];
        for(String condition : conditionBlock) {
            Matcher varNameM = VALID_VARIABLE_CALL.matcher(condition);
            if(varNameM.matches()) {

            }
        }
    }

}
