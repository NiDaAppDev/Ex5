package ex5.reg_ex_patterns;

import java.util.regex.Pattern;

import static ex5.reg_ex_patterns.GeneralRegExPatterns.*;
import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

/**
 * A class that holds regexes relevant to methods.
 */
public class MethodRegExPatterns {

    private static final String METHOD_NAME = "\\b[a-zA-Z]\\w*\\b";
    private static final String METHOD_SIGNATURE =
            SPACES_INITIAL + "void" + SPACE_CHAR + "+(" + METHOD_NAME + ")" +
                    SPACE_CHAR + "*\\(" +
                    "([^)(]*)\\)" + SPACE_CHAR + "*\\{" + SPACES_ENDING;
    /*
     * Relevant to the statement above:
     * Group 1 captures the methods' name
     * Group 2 captures the method's parameters
     * */

    private static final String SINGLE_PARAMETER =
            "(" + VARIABLE_TYPE + ")" + SPACE_CHAR +
                    "+(" + VAR_NAME + ")" + SPACE_CHAR + "*";
    private static final String PARAMETERS =
            "(?:" + SINGLE_PARAMETER + "(?:," + SINGLE_PARAMETER + ")*" + ")?";

    private static final String METHOD_CALL =
            SPACE_CHAR + "*(" + METHOD_NAME + ")" +
                    SPACE_CHAR + "*\\(" +
                    "([^)]*)\\)" + SPACE_CHAR + "*;";
    /*
     * Group 1 captures the method's name
     * Group 2 captures the parameters block
     */

    private static final String RETURN_STATEMENT =
            SPACES_INITIAL + "return" + ENDING_SEMICOLON;

    /**
     * A pattern to validate a method definition.
     */
    public static final Pattern METHOD_PATTERN =
            Pattern.compile(METHOD_SIGNATURE);

    /**
     * A pattern to validate a method call.
     */
    public static final Pattern METHOD_CALL_PATTERN =
            Pattern.compile(METHOD_CALL);

    /**
     * A pattern to validate a return line.
     */
    public static final Pattern RETURN_STATEMENT_PATTERN =
            Pattern.compile(RETURN_STATEMENT);
}
