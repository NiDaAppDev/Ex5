package ex5.utils;

import ex5.components.Scope;
import ex5.components.Variable;
import ex5.IllegalException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.GeneralRegExPatterns.*;
import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

public class MethodUtils {

    public static List<NameVariablePair> extractMethodParameters(String data) {
        List<NameVariablePair> pairs = new ArrayList<>();
        if (data == null) {
            return pairs;
        }

        String[] paramNames = data.split(",");
        for (String paramGroup : paramNames) {
            paramGroup = paramGroup.trim();
            if (paramGroup.isEmpty()) continue;
            boolean isParamFinal = false;
            if (paramGroup.startsWith(FINAL)) {
                isParamFinal = true;
                paramGroup = paramGroup.substring(5).trim();
            }
            String[] paramParts = paramGroup.split(SPACE_CHAR + "+");
            String type = paramParts[0];
            String name = paramParts[1];
            Variable var = new Variable(isParamFinal, type, true);
            pairs.add(new NameVariablePair(name, var));
        }
        return pairs;
    }

    public static List<Variable> getArgsFromCall(String data, Scope currScope) throws IllegalException {
        List<Variable> result = new ArrayList<>();

        if (data == null) return result;

        String[] rawArgs = data.split(",");

        for (String arg : rawArgs) {
            arg = arg.trim();
            Variable var = null;
            if (currScope.getAllVisibleVariables().containsKey(arg)) {
                var = currScope.getAllVisibleVariables().get(arg);
            }
            else {
                String constantType = getConstantType(arg);
                if (constantType != null) {
                    var = new Variable(false, constantType, true);
                }
            }
            if (var == null) {
                throw new IllegalException("Couldn't parse one or more of a method parameters.");
            }
            result.add(var);
        }
        return result;
    }

    private static String getConstantType(String arg) {
        Matcher matcher = VALUE_TYPE_PATTERN.matcher(arg);
        if (!matcher.matches()) return null;
        if (matcher.group(1) != null) return INT_TYPE;
        if (matcher.group(2) != null) return DOUBLE_TYPE;
        if (matcher.group(3) != null) return STRING_TYPE;
        if (matcher.group(4) != null) return BOOLEAN_TYPE;
        if (matcher.group(5) != null) return CHAR_TYPE;
        return null;
    }

    public static String[] extractCallParams(String data) {
        if (data == null) return new String[0];
        String[] params = data.split(",");
        for (int i = 0; i < params.length; i++) {
            params[i] = params[i].trim();
        }
        return params;
    }
}