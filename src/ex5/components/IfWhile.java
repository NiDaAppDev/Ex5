package ex5.components;

public class IfWhile {

    Scope scope;

    public IfWhile(String condition , String[] codeLines) {
        if(!isConditionValid(condition)) {
            //TODO: throw an "illegal" exception.
        }

    }

    private boolean isConditionValid(String condition) {
        //TODO: how do we validate a condition?
        return true;
    }

}
