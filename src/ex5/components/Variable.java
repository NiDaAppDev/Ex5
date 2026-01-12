package ex5.components;

public class Variable {

    private final String type;
    private final boolean isFinal;
    private boolean isInitialized;

    public Variable(String type, boolean isFinal, boolean isInitialized) {
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
