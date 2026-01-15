package ex5.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.VariableRegExPatterns.VALID_VARIABLE_CALL;

import static ex5.reg_ex_patterns.IfWhileRegExPatterns.CONDITION_BLOCK_PATTERN;

public class IfWhileUtils {

    public static ArrayList<String> extractVarNamesInCondition(String conditionBlock) throws Exception {
        Matcher blockMatcher = CONDITION_BLOCK_PATTERN.matcher(conditionBlock);
        if (!blockMatcher.matches()) throw new Exception();

        ArrayList<String> varNames = new ArrayList<>();
        Matcher varNameM = VALID_VARIABLE_CALL.matcher(conditionBlock);
        while (varNameM.find()) {
            String varName = varNameM.group();
            if (!varName.equals("true") && !varName.equals("false") && !varName.matches("-?\\d+.*")) {
                varNames.add(varName);
            }
        }
        return varNames;
    }

    private static String[] extractConditionsFromBlock(String conditionBlock) {
        Matcher conditionMatcher = CONDITION_BLOCK_PATTERN.matcher(conditionBlock);
        if (!conditionMatcher.matches()) return new String[0];
        String[] conditions =  new String[conditionMatcher.groupCount()];
        for(int i = 0; i < conditions.length; i++) {
            conditions[i] = conditionMatcher.group(i);
        }
        return conditions;
    }

}
