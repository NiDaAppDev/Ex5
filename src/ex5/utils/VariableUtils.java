package ex5.utils;

import ex5.components.Variable;
import ex5.IllegalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

/**
 * Utils for variables.
 */
public class VariableUtils {

    /**
     * @param globalVariables are the variables that are visible on the declaration stage.
     * @param data is an array of the declaration data collected.
     * @return a list of the declared variables.
     * @throws IllegalException is thrown if one of the variables was declared wrongly.
     */
    public static ArrayList<NameVariablePair> extractDeclaredVariables(
            HashMap<String, Variable> globalVariables,
            String[] data)
            throws IllegalException {
        boolean isFinal = data[1] != null;
        String type = data[2];

        Matcher varDeclarationsM = VARIABLE_DEFENITION_PATTERN.matcher(data[3]);
        ArrayList<NameVariablePair> vars = new ArrayList<>();
        while (varDeclarationsM.find()) {
            String[] varInitData = varDeclarationsM.group().trim().split("=");
            String name = varInitData[0].trim();
            vars.add(new NameVariablePair(name, new Variable(isFinal, type, false)));
            if (varInitData.length < 2) {
                if(isFinal) {
                    throw new IllegalException("Final variable wasn't initialized");
                }
                continue;
            }
            String value = varInitData[1];
            value = value.trim();
            assignValueToVariable(globalVariables, vars.get(vars.size()-1).getVariable(), value);
        }
        return vars;
    }

    /**
     * @param assignmentStatement is the statement of a variable assignment.
     * @return the variable assignment data - the assigned-to variable's name, and the value that
     * was assigned to it.
     */
    public static VariableAssignmentPair extractVariableAssignment(String assignmentStatement) {
        String[] varInitData = assignmentStatement.trim().split("=");
        String name = varInitData[0].trim();
        String value = varInitData[1].trim();
        return new VariableAssignmentPair(name, value);
    }

    /**
     * @param globalVariables are the variables that are visible at the assignment's stage.
     * @param variable is the variable we try to assign to.
     * @param value is the value assigned to the variable.
     * @throws IllegalException is thrown if the assignment is illegal.
     */
    public static void assignValueToVariable(HashMap<String, Variable> globalVariables,
                                             Variable variable,
                                             String value)
            throws IllegalException {
        if (!isValueAssignmentLegal(variable.getType(), value) &&
                !isVariableAssignmentLegal(globalVariables, variable.getType(), value)) {
            throw new IllegalException("Illegal variable assignment.");
        }
        variable.initialize();
    }

    private static boolean isValueAssignmentLegal(String type, String value) {
        Matcher valueTypeM = VALUE_TYPE_PATTERN.matcher(value);
        return valueTypeM.matches() &&
                (!type.equals(STRING_TYPE) || valueTypeM.group(1) != null) &&
                (!type.equals(CHAR_TYPE) || valueTypeM.group(2) != null) &&
                (!type.equals(BOOLEAN_TYPE) ||
                        (valueTypeM.group(3) != null ||
                                valueTypeM.group(4) != null ||
                                valueTypeM.group(5) != null)) &&
                (!type.equals(INT_TYPE) || (valueTypeM.group(4) != null)) &&
                (!type.equals(DOUBLE_TYPE) ||
                        (valueTypeM.group(4) != null ||
                                valueTypeM.group(5) != null));
    }

    private static boolean isVariableAssignmentLegal(HashMap<String, Variable> globalVariables,
                                                     String variableType,
                                                     String assignedVarName) {
        if (!globalVariables.containsKey(assignedVarName) ||
                !globalVariables.get(assignedVarName).isInitialized()) {
            return false;
        }
        String assignedVarType = globalVariables.get(assignedVarName).getType();
        return areTypesCompatible(variableType, assignedVarType);
    }

    private static boolean areTypesCompatible(String targetType, String assignedType) {
        return targetType.equals(assignedType) ||
                targetType.equals(DOUBLE_TYPE) && assignedType.equals(INT_TYPE) ||
                targetType.equals(BOOLEAN_TYPE) && (assignedType.equals(INT_TYPE)
                        || assignedType.equals(DOUBLE_TYPE));
    }

}
