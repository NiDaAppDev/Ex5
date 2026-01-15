package ex5.utils;

import ex5.components.Variable;

import java.util.ArrayList;
import java.util.List;

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
            if (paramGroup.startsWith("final")) {
                isParamFinal = true;
                paramGroup = paramGroup.substring(5).trim();
            }
            String[] paramParts = paramGroup.split("\\s+");
            String type = paramParts[0];
            String name = paramParts[1];
            Variable var = new Variable(isParamFinal, type, true);
            pairs.add(new NameVariablePair(name, var));
        }
        return pairs;
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