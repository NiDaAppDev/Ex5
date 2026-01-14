package ex5.components;

public class IfWhile {

    Scope scope;

    public IfWhile(String[] conditionBlock , String[] codeLines) {
        if(!isConditionBlockValid(conditionBlock)) {
            //TODO: throw an "illegal" exception.
        }

    }

    private boolean isConditionBlockValid(String[] conditionBlock) {
        //TODO: how do we validate a condition?
        return true;
    }

}
