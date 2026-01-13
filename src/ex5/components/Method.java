package ex5.components;

import java.util.List;

public class Method {
    private final String methodName;
    private final List<String> paramNames;
    private final List<String> paramTypes;
    private String codeBlock;
    private final boolean isFinal;

    public Method(String[] groups, String[] codeLines) {

    }

    public String getMethodName() {
        return methodName;
    }

    public String getCodeBlock() {
        return codeBlock;
    }


}
