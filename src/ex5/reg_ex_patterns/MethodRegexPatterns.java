package ex5.reg_ex_patterns;

import java.util.regex.Pattern;

import static ex5.reg_ex_patterns.GeneralRegExPatterns.*;
import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

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
    /*
    * Relevant to the statement above:
    * Group 1 captures a single variable's type
    * Group 2 captures a single variable's name
    */

    private static final String PARAMETERS =
            "(?:" + SINGLE_PARAMETER + "(?:," + SINGLE_PARAMETER + ")*" + ")?";

    private static final String METHOD_CALL =
            SPACES_INITIAL + "(" + VAR_NAME + ")" + SPACE_CHAR + "*" +
            "\\((.*)\\)" + ENDING_SEMICOLON;
    /*
    * Group 1 captures the input parameters
    * */

    public static final Pattern  METHOD_PATTERN =
            Pattern.compile(METHOD_SIGNATURE);

    public static final Pattern  PARAMETERS_PATTERN =
            Pattern.compile(PARAMETERS);

    public static final Pattern  METHOD_CALL_PATTERN =
            Pattern.compile(METHOD_CALL);
}
