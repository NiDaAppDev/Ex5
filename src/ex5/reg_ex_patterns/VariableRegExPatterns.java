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
    private static final String INT_TYPE = "int";
    private static final String DOUBLE_TYPE = "double";
    private static final String STRING_TYPE = "String";
    private static final String BOOLEAN_TYPE = "boolean";
    private static final String CHAR_TYPE = "char";
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
    private static final String INT_VALUE = PLUS_MINUS + "?\\d+";
    private static final String DOUBLE_VALUE = PLUS_MINUS + "?(?:\\d+\\.?\\d*|\\.\\d+)";
    private static final String STRING_VALUE = "\"[^\"]*\"";
    private static final String BOOLEAN_VALUE = "true" + OR + "false" + OR + DOUBLE_VALUE;
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
    private static final String VALID_VARIABLE_NAME_ONLY = "^" + VAR_NAME + "$";
    /*
     * Relevant to the statement above:
     * Group 1 captures the variable's name
     */

    //Initialized variable
    private static final String VALID_VARIABLE_ASSIGNMENT =
            "^" + VAR_NAME + EQ + VARIABLE_VALUE + "$";
    /*
     * Relevant to the statement above:
     * Group 1 captures the variable's name
     * Group 2 captures the variable's value
     */

    private static final String SINGLE_VARIABLE_INIT_VALID_CONTENT =
            VALID_VARIABLE_NAME_ONLY + OR + VALID_VARIABLE_ASSIGNMENT;

    /**
     * This pattern should be used to validate the whole structure of a variable's declaration line.
     */
    public static final Pattern variableDeclarationPattern =
            Pattern.compile(VARIABLE_DECLARATION_LINE);

    /**
     * This pattern should be used to validate each and every variable initialization in a line of
     * variables declaration.
     * Should be checked on capturing group 3 of VARIABLE_DECLARATION_LINE's result with find()
     * method.
     * TODO: if more than 1 variable is defined in the same definition, make sure the initializations
     * TODO: are separated by a comma.
     */
    public static final Pattern singleVariableInitPattern =
            Pattern.compile(SINGLE_VARIABLE_INIT_VALID_CONTENT);

    /**
     * This pattern should be used to validate an assignment of a value to a variable.
     */
    public static final Pattern assignValueToVariablePattern =
            Pattern.compile(VALID_VARIABLE_ASSIGNMENT);

}
