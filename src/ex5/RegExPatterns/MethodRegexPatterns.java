package ex5.RegExPatterns;

import java.util.regex.Pattern;

import static ex5.RegExPatterns.GeneralRegEx.SPACE_CHAR;
import static ex5.RegExPatterns.VariableRegExPatterns.*;

public class MethodRegexPatterns {

    private static final String METHOD_NAME = "\\b[a-zA-Z]\\w*\\b";
    private static final String METHOD_PARAMETER =
            VARIABLE_TYPE + SPACE_CHAR + "+" + VAR_NAME;
    private static final String METHOD_SIGNATURE =
            "void" + SPACE_CHAR + "+" + METHOD_NAME +
                    SPACE_CHAR + "*\\(" +
                    "([^)]+)\\)" + SPACE_CHAR + "*\\{";
    /*
    * Relevant to the statement above:
    * Group 1 captures the methods' parameters
    * */

    private static final String SINGLE_PARAMETER =
            "(" + VARIABLE_TYPE + ")" + SPACE_CHAR +
                    "+(" + VAR_NAME + ")" + SPACE_CHAR + "*";
    private static final String PARAMETERS =
            "(?:" + SINGLE_PARAMETER + "(?:," + SINGLE_PARAMETER + ")*" + ")?";

    public static final Pattern  METHOD_PATTERN =
            Pattern.compile(METHOD_SIGNATURE);

    public static final Pattern  PARAMETERS_PATTERN =
            Pattern.compile(PARAMETERS);

}
