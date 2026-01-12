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

    public Variable(String type, boolean isInitialized) {
        this.type = type;
        this.isFinal = false;
        this.isInitialized = isInitialized;
    }

    public void initialize() {
        if(!isInitialized) {
            this.isInitialized = true;
        }
    }

}
