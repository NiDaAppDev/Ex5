package ex5.reg_ex_patterns;

import java.util.regex.Pattern;

import static ex5.reg_ex_patterns.GeneralRegExPatterns.*;

public class VariableRegExPatterns {

    /*Keyword Constants*/

    //General
    private static final String PLUS_MINUS = "[+\\-]";
    private static final String OPERATOR = "[+\\-\\*\\/]";
    private static final String ARRAY_BRACKET = "[\\[\\]]";

    //Variables Basics
    public static final String FINAL = "final";
    public static final String INT_TYPE = "int";
    public static final String DOUBLE_TYPE = "double";
    public static final String STRING_TYPE = "String";
    public static final String BOOLEAN_TYPE = "boolean";
    public static final String CHAR_TYPE = "char";

    public static final String VARIABLE_TYPE =
            INT_TYPE + OR +
                    DOUBLE_TYPE + OR +
                    STRING_TYPE + OR +
                    BOOLEAN_TYPE + OR +
                    CHAR_TYPE;

    public static final String VAR_NAME = "\\b(?:[a-zA-Z]\\w*|_[a-zA-Z0-9]\\w*)\\b";

    public static final String INT_VALUE = PLUS_MINUS + "?\\d+";
    public static final String DOUBLE_VALUE = PLUS_MINUS + "?(?:\\d+\\.?\\d*|\\.\\d+)";
    private static final String STRING_VALUE = "\"[^\"]*\"";
    public static final String BOOLEAN_VALUE = "true" + OR + "false";

    private static final String CHAR_VALUE = "'[^']'";
    private static final String VARIABLE_VALUE =
            INT_VALUE + OR +
                    DOUBLE_VALUE + OR +
                    STRING_VALUE + OR +
                    BOOLEAN_VALUE + OR +
                    CHAR_VALUE + OR +
                    VAR_NAME;

    //Operators
    private static final String EQ = SPACE_CHAR + "*=" + SPACE_CHAR + "*";
    private static final String COMMA = SPACE_CHAR + "*," + SPACE_CHAR + "*";

    //Uninitialized variable, name only
    private static final String VALID_VARIABLE_NAME_ONLY = VAR_NAME;

    //Initialized variable
    private static final String VALID_VARIABLE_ASSIGNMENT =
            VAR_NAME + EQ + VARIABLE_VALUE;

    private static final String VALID_VARIABLE_ASSIGNMENT_LINE =
            SPACES_INITIAL + "(" + VALID_VARIABLE_ASSIGNMENT + ")" +
                    SPACE_CHAR + "*" + ENDING_SEMICOLON + SPACES_ENDING;

    private static final String SINGLE_VARIABLE_INIT_VALID_CONTENT =
            VALID_VARIABLE_ASSIGNMENT + OR + VALID_VARIABLE_NAME_ONLY;

    private static final String VARIABLES_DECLARATION_CONTENT =
            "(" + SINGLE_VARIABLE_INIT_VALID_CONTENT + ")(?:" +
                    SPACE_CHAR + "*," + SPACE_CHAR + "*(" +
                    SINGLE_VARIABLE_INIT_VALID_CONTENT + "))*";

    //Variables Initialization
    private static final String VARIABLE_DECLARATION_LINE =
            "\\s*"
                    + "(" + FINAL + SPACE_CHAR + "+)?"
                    + "(" + VARIABLE_TYPE + ")\\s+"
                    + "(" + VARIABLES_DECLARATION_CONTENT + ")"
                    + ENDING_SEMICOLON;

    private static final String VALUE_TYPE =
            "(" + STRING_VALUE + ")" + OR +      // Group 1: String
                    "(" + CHAR_VALUE + ")" + OR +        // Group 2: Char
                    "(" + BOOLEAN_VALUE + ")" + OR +     // Group 3: Boolean only
                    "(" + INT_VALUE + ")" + OR +         // Group 4: Int (check before double)
                    "(" + DOUBLE_VALUE + ")";            // Group 5: Double

    public static final Pattern VARIABLE_DECLARATION_PATTERN =
            Pattern.compile(VARIABLE_DECLARATION_LINE);

    public static final Pattern VARIABLE_DEFENITION_CONTENT_PATTERN =
            Pattern.compile(VARIABLES_DECLARATION_CONTENT);

    public static final Pattern VALUE_TYPE_PATTERN =
            Pattern.compile(VALUE_TYPE);

    public static final Pattern ASSIGN_VALUE_TO_VARIABLE_PATTERN =
            Pattern.compile(VALID_VARIABLE_ASSIGNMENT_LINE);

    public static final Pattern VALID_VARIABLE_CALL =
            Pattern.compile(VAR_NAME);

    public static final Pattern FIND_SINGLE_VAR_PATTERN =
            Pattern.compile(SINGLE_VARIABLE_INIT_VALID_CONTENT);
}