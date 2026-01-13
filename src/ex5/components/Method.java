package ex5.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Method {
    private Scope scope;
    private final String methodName;
    private final HashMap<String, Variable> parameters;
    private final List<String> paramTypes;
    private String[] methodBody;

    public Method(String[] groups, String[] codeLines, Scope parent) {
        this.methodName = groups[1];
        this.methodBody = codeLines;
        this.parameters = new HashMap<>();
        this.paramTypes = new ArrayList<>();

        String paramString = groups[2];
        if (paramString != null && !paramString.isEmpty()) {
            parseParameters(paramString);
        }

        this.scope = new Scope(parent,
                codeLines,
                parameters);
    }

    private void parseParameters(String paramString) {
        String[] paramGroups = paramString.split(",");
        for (String paramGroup : paramGroups) {
            paramGroup = paramGroup.trim();
            boolean isParamFinal = false;
            if (paramGroup.startsWith("final")) {
                isParamFinal = true;
                paramGroup = paramGroup.substring(5).trim();
            }
            String[] paramParts = paramGroup.split("\\s+");
            String type = paramParts[0];
            String name = paramParts[1];
            Variable var = new Variable(isParamFinal, type, false);
            parameters.put(name, var);
            paramTypes.add(type);
        }
    }

    public void call(Varialbe[] params) {
        
    }
}
