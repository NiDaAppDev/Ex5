package ex5.RegExPatterns;

import java.util.regex.Pattern;

import static ex5.RegExPatterns.GeneralRegEx.OR;
import static ex5.RegExPatterns.GeneralRegEx.SPACES_INITIAL;

public class IgnoreLineRegEx {

    private static final String EMPTY_LINE = SPACES_INITIAL + "$";
    private static final String COMMENT_LINE = "^//";
    private static final String IGNORABLE_LINE = EMPTY_LINE + OR + COMMENT_LINE;

//    private static final String ILLEGAL_ONE_LINE_COMMENT = ".+\\/\\/";
//    private static final String ILLEGAL_MULTILINE_COMMENT_OPENER = "[^\\/]?\\/\\*";
//    private static final String ILLEGAL_COMMENT_LINE =
//            ILLEGAL_ONE_LINE_COMMENT + OR + ILLEGAL_MULTILINE_COMMENT_OPENER;

    /**
     * This pattern should be used to classify a comment\spaces line that's ignorable.
     */
    public static final Pattern ignoreLinePattern = Pattern.compile(IGNORABLE_LINE);


    //TODO: figure out if we should actually refer to illegal comment openers, or they just won't
    //TODO: fit the exact pattern our program should recognize.
//    /**
//     * This pattern should be used to check if a line contains an illegal comment opener
//     */
//    public static final Pattern illegalCommentLinePattern = Pattern.compile(ILLEGAL_ONE_LINE_COMMENT);

}
