package ex5.utils;

/**
 * An object that stores a line's analysis data.
 */
public class LineAnalysis {

    private final LineReader.LINE_TYPE type;
    private final String[] groups;

    /**
     * @param type is the classified type of the line.
     * @param groups are the captured groups in the line.
     */
    public LineAnalysis(LineReader.LINE_TYPE type, String[] groups) {
        this.type = type;
        this.groups = groups;
    }

    /**
     * Getter.
     * @return the line type that was found.
     */
    public LineReader.LINE_TYPE getType() {
        return type;
    }

    /**
     * Getter
     * @return the groups that were found.
     */
    public String[] getGroups() {
        return groups;
    }
}
