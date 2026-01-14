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
    private static final String FINAL = "final";
    /**
     * Regex that recognizes an int type.
     */
    public static final String INT_TYPE = "int";
    /**
     * Regex that recognizes a double type.
     */
    public static final String DOUBLE_TYPE = "double";
    /**
     * Regex that recognizes a String type.
     */
    public static final String STRING_TYPE = "String";
    /**
     * Regex that recognizes a boolean type.
     */
    public static final String BOOLEAN_TYPE = "boolean";
    /**
     * Regex that recognizes a char type.
     */
    public static final String CHAR_TYPE = "char";
    /**
     * Regex that validates a variable's type.
     */
    public static final String VARIABLE_TYPE =
            INT_TYPE + OR +
                    DOUBLE_TYPE + OR +
                    STRING_TYPE + OR +
                    BOOLEAN_TYPE + OR +
                    CHAR_TYPE;
    /**
     * Regex that validates a variable's name.
     */
    public static final String VAR_NAME = "\\b(?:[a-zA-Z]\\w*|_[a-zA-Z0-9]\\w*)\\b";
    /**
     * Regex that validates an int value.
     */
    public static final String INT_VALUE = PLUS_MINUS + "?\\d+";
    /**
     * Regex that validates a double value.
     */
    public static final String DOUBLE_VALUE = PLUS_MINUS + "?(?:\\d+\\.?\\d*|\\.\\d+)";
    private static final String STRING_VALUE = "\"[^\"]*\"";
    /**
     * Regex that validates a boolean value.
     */
    public static final String BOOLEAN_VALUE = "true" + OR + "false" + OR + DOUBLE_VALUE;
    private static final String CHAR_VALUE = "'[^']'";
    private static final String VARIABLE_VALUE =
            INT_VALUE + OR +
                    DOUBLE_VALUE + OR +
                    STRING_VALUE + OR +
                    BOOLEAN_VALUE + OR +
                    CHAR_VALUE;

    //Operators
    private static final String EQ = SPACE_CHAR + "*=" + SPACE_CHAR + "*";
    private static final String COMMA = SPACE_CHAR + "*," + SPACE_CHAR + "*";

    //Variables Initialization
    private static final String VARIABLE_DECLARATION_LINE =
            "\\s*" //Line can start with spaces
                    + "(" + FINAL + SPACE_CHAR + "+)?" //Optional final, if exists must have space(s) after
                    + "(" + VARIABLE_TYPE + ")\\s+" //Variable type must have right space(s) after it.
                    + "([^;]+)" + ENDING_SEMICOLON; /*A line ends with a semicolon, and we capture the
            content of the variable definition (later to be extracted as names, and optional values)*/
    /*
     * Relevant to the statement above:
     * Group 1 captures whether the variable is final or not (captures final if it is, null if not)
     * Group 2 captures the variable's type
     * Group 3 captures the content of the variables' definitions - later to be validated
     */

    //Uninitialized variable, name only
    private static final String VALID_VARIABLE_NAME_ONLY = VAR_NAME;

    //Initialized variable
    private static final String VALID_VARIABLE_ASSIGNMENT =
            VAR_NAME + EQ + VARIABLE_VALUE;

    private static final String VALID_VARIABLE_ASSIGNMENT_LINE =
            SPACES_INITIAL + "(" + VALID_VARIABLE_ASSIGNMENT + ")" +
                    SPACE_CHAR + "*" + ENDING_SEMICOLON + SPACES_ENDING;
    /*
     * Group 1 captures an assignment of a value to a variable
     */

    private static final String SINGLE_VARIABLE_INIT_VALID_CONTENT =
            VALID_VARIABLE_NAME_ONLY + OR + VALID_VARIABLE_ASSIGNMENT;

    private static final String VARIABLES_DECLARATION_CONTENT =
            "(" + SINGLE_VARIABLE_INIT_VALID_CONTENT + ")(?:" +
                    SPACE_CHAR + "*," + SPACE_CHAR + "*(" +
                    SINGLE_VARIABLE_INIT_VALID_CONTENT + "))*";
    /*
     * Relevant to the statement above:
     * Captured groups hold the variables' in the declaration
     */

    private static final String VALUE_TYPE =
            "(" + INT_VALUE + ")" + OR +
                    "(" + DOUBLE_VALUE + ")" + OR +
                    "(" + STRING_VALUE + ")" + OR +
                    "(" + BOOLEAN_VALUE + ")" + OR +
                    "(" + CHAR_VALUE + ")";
    /*
     * Relevant to the statement above:
     * Group 1 captures if the value is an integer
     * Group 2 captures if the value is a double
     * Group 3 captures if the value is a string
     * Group 4 captures if the value is a boolean
     * Group 5 captures if the value is a char
     */

    /**
     * This pattern should be used to validate the whole structure of a variable's declaration line.
     */
    public static final Pattern VARIABLE_DECLARATION_PATTERN =
            Pattern.compile(VARIABLE_DECLARATION_LINE);


    /**
     * This pattern should be used to validate and extract the declared variables in a variable
     * declaration line.
     */
    public static final Pattern VARIABLE_DEFENITION_CONTENT_PATTERN =
            Pattern.compile(VARIABLES_DECLARATION_CONTENT);

    /**
     * This pattern should be used to parse the type of a value.
     */
    public static final Pattern VALUE_TYPE_PATTERN =
            Pattern.compile(VALUE_TYPE);

    /**
     * This pattern should be used to validate an assignment of a value to a variable.
     */
    public static final Pattern ASSIGN_VALUE_TO_VARIABLE_PATTERN =
            Pattern.compile(VALID_VARIABLE_ASSIGNMENT_LINE);

    /**
     * This pattern should be used to validate a call to a variable (usually
     * as a boolean in a condition)
     */
    public static final Pattern VALID_VARIABLE_CALL =
            Pattern.compile(VAR_NAME);

}
