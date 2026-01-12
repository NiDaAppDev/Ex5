package ex5.RegExPatterns;

import static ex5.RegExPatterns.GeneralRegEx.SPACES_INITIAL;

public class IgnoreLineRegEx {

    private static final String EMPTY_LINE = SPACES_INITIAL + "$";
    private static final String COMMENT_LINE = "^\\/\\/";
    private static final String ILLEGAL_ONE_LINE_COMMENT = ".+\\/\\/";
    private static final String ILLEGAL_MULTILINE_COMMENT_OPENER = "[^\\/]?\\/\\*";

}
