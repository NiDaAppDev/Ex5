package ex5.components;

public class Variable {

    private final boolean isFinal;
    private final String type;
    private boolean isInitialized;

    public Variable(boolean isFinal, String type, boolean isInitialized) {
        this.type = type;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public String getType() {
        return type;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void initialize() {
        isInitialized = true;
    }
}
