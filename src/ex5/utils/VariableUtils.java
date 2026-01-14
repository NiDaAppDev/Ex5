package ex5.utils;

import ex5.components.Variable;

import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

public class VariableUtils {

    public static NameVariablePair[] extractDeclaredVariables(String[] data) {
        boolean isFinal = data[1] != null;
        String type = data[2];

        Matcher varDeclarationsM = VARIABLE_DEFENITION_CONTENT_PATTERN.matcher(data[3]);
        NameVariablePair[] vars = new NameVariablePair[varDeclarationsM.groupCount()];
        for (int i = 1; i <= varDeclarationsM.groupCount(); i++) {
            String[] varInitData = varDeclarationsM.group(i).trim().split("=");
            String name = varInitData[0].trim();
            String value = varInitData[1];
            vars[i] = new NameVariablePair(name, new Variable(isFinal, type, false));
            if(value == null) {
                continue;
            }
            value = value.trim();
            assignValueToVariable(vars[i].getVariable(), value);
        }
        return vars;
    }

    public static VariableAssignmentPair extractVariableAssignment(String assignmentStatement) {
        String[] varInitData = assignmentStatement.trim().split("=");
        String name = varInitData[0].trim();
        String value = varInitData[1].trim();
        return new VariableAssignmentPair(name, value);
    }

    public static void assignValueToVariable(Variable variable, String value) {
        if(!isAssignmentLegal(variable.getType(), value)) {
            //TODO: throw an "illegal" exception.
        }
        variable.initialize();
    }

    private static boolean isAssignmentLegal(String type, String value) {
        Matcher valueTypeM = VALUE_TYPE_PATTERN.matcher(value);
        return valueTypeM.matches() &&
                (!type.equals("int") || valueTypeM.group(1) != null) &&
                (!type.equals("double") || valueTypeM.group(2) != null) &&
                (!type.equals("string") || valueTypeM.group(3) != null) &&
                (!type.equals("boolean") || valueTypeM.group(4) != null) &&
                (!type.equals("char") || valueTypeM.group(5) != null);
    }

}
