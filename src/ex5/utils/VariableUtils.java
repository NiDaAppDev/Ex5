package ex5.utils;

import ex5.components.Scope;
import ex5.components.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

public class VariableUtils {

    public static NameVariablePair[] extractDeclaredVariables(String[] data, Scope scope) throws Exception {
        boolean isFinal = data[1] != null;
        String type = data[2];
        String content = data[3];

        List<NameVariablePair> varsList = new ArrayList<>();
        Matcher matcher = FIND_SINGLE_VAR_PATTERN.matcher(content);
        while (matcher.find()) {
            String match = matcher.group().trim();
            String name;
            String value = null;
            boolean isInitialized = false;

            if (match.contains("=")) {
                String[] parts = match.split("=");
                name = parts[0].trim();
                if (parts.length > 1) {
                    value = parts[1].trim();
                    isInitialized = true;
                }
            } else {
                name = match.trim();
            }
            if (isFinal && !isInitialized){
                throw new Exception();
            }

            Variable newVar = new Variable(isFinal, type, isInitialized);
            if (isInitialized) {
                if (value == null) throw new Exception();
                validateAndAssign(newVar, value, scope);
        }
            varsList.add(new NameVariablePair(name, newVar));
    }
        return varsList.toArray(new NameVariablePair[0]);
}

public static VariableAssignmentPair extractVariableAssignment(String assignmentStatement) {
    String[] varInitData = assignmentStatement.trim().split("=");
    String name = varInitData[0].trim();
    String value = varInitData[1].trim();
    return new VariableAssignmentPair(name, value);
}

public static void validateAndAssign(Variable variable, String value, Scope scope) throws Exception {
        if (isAssignmentLegal(variable.getType(), value)) {
            variable.initialize();
            return;
        }
        if (scope != null && scope.getAllVisibleVariables().containsKey(value)) {
            Variable srcVar = scope.getAllVisibleVariables().get(value);
            if (!srcVar.isInitialized()) {
                throw new Exception();
            }
            if (areTypesCompatible(variable.getType(), srcVar.getType())){
                variable.initialize();
                return;
            }
        }
        throw new Exception();
}
public static void assignValueToVariable(Variable variable, String value) throws Exception {
    if (!isAssignmentLegal(variable.getType(), value)) {
       throw new Exception();
    }
    variable.initialize();
}

private static boolean isAssignmentLegal(String type, String value) {
    Matcher valueTypeM = VALUE_TYPE_PATTERN.matcher(value);
    if (!valueTypeM.matches()) return false;

    if (type.equals(INT_TYPE)) {
        return valueTypeM.group(4) != null; // only integers
    }

    if (type.equals(DOUBLE_TYPE)) {
        // double accepts double OR integer
        return (valueTypeM.group(5) != null) || (valueTypeM.group(4) != null);
    }

    if (type.equals(STRING_TYPE)) {
        return valueTypeM.group(1) != null;
    }

    if (type.equals(BOOLEAN_TYPE)) {
        // boolean accepts boolean OR numeric values
        return (valueTypeM.group(3) != null) || (valueTypeM.group(4) != null) || (valueTypeM.group(5) != null);
    }

    if (type.equals(CHAR_TYPE)) {
        return valueTypeM.group(2) != null;
    }

    return false;
}

    private static boolean areTypesCompatible(String targetType, String sourceType) {
        if (targetType.equals(sourceType)) return true;
        if (targetType.equals(DOUBLE_TYPE) && sourceType.equals(INT_TYPE)) return true;
        if (targetType.equals(BOOLEAN_TYPE) && (sourceType.equals(INT_TYPE)
                || sourceType.equals(DOUBLE_TYPE))) return true;
        return false;
    }
}
