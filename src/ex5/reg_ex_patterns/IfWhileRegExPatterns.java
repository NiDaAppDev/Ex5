package ex5.reg_ex_patterns;

import java.util.regex.Pattern;

import static ex5.reg_ex_patterns.GeneralRegExPatterns.*;
import static ex5.reg_ex_patterns.VariableRegExPatterns.*;

public class IfWhileRegExPatterns {
    private static final String IF_WHILE_DECLARATION_LINE =
            SPACES_INITIAL //Line can start with spaces
                    + "(?:if|while)" + SPACE_CHAR + "*" //'if' keyword may have spaces after it
                    + "\\(" //Opening parenthesis
                    + "([^)]+)\\)" //Condition
                    + SPACE_CHAR + "*" //There could be spaces between the condition and parentheses
                    + "\\{" //The body of the if statement starts with a curly bracket
                    + SPACES_ENDING; //Line can end with spaces
    /*
     * Relevant to the statement above:
     * Group 1 captures the condition of the if\while statement.
     */

    private static final String SINGLE_VALID_CONDITION =
            BOOLEAN_VALUE + OR +
                    INT_VALUE + OR +
                    DOUBLE_VALUE + OR +
                    VAR_NAME;
    private static final String SINGLE_VALID_BOOLEAN =
            BOOLEAN_VALUE + OR +
                    DOUBLE_VALUE + OR +
                    INT_VALUE;
    private static final String VALID_CONDITION_BLOCK =
            SPACE_CHAR + "*(" + SINGLE_VALID_CONDITION + ")" + SPACE_CHAR +
                    "*(?:(?:\\|\\|" + OR + "&&)" + SPACE_CHAR +
                    "*(" + SINGLE_VALID_CONDITION + ")" + SPACE_CHAR +"*)*";
    /*
     * Relevant to the statement above:
     * Captured groups hold the conditions constructing the conditions block
     */

    /**
     * This pattern should be used to validate the title of an if\while statement.
     */
    public static final Pattern IF_WHILE_PATTERN = Pattern.compile(IF_WHILE_DECLARATION_LINE);

    /**
     * This pattern should be used to validate a single condition.
     */
    public static final Pattern SINGLE_CONDITION_PATTERN = Pattern.compile(SINGLE_VALID_CONDITION);

    /**
     * This pattern should be used to validate a condition block.
     */
    public static final Pattern CONDITION_BLOCK_PATTERN = Pattern.compile(VALID_CONDITION_BLOCK);

    /**
     * This pattern should be used to validate a single condition that isn't a variable name.
     */
    public static final Pattern BOOLEAN_CONST_PATTERN = Pattern.compile(SINGLE_VALID_BOOLEAN);
}
