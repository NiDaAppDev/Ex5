package ex5.utils;

public class LineAnalysis {

    private final LineReader.LINE_TYPE type;
    private final String[] groups;

    public LineAnalysis(LineReader.LINE_TYPE type, String[] groups) {
        this.type = type;
        this.groups = groups;
    }

    public LineReader.LINE_TYPE getType() {
        return type;
    }

    public String[] getGroups() {
        return groups;
    }
}
