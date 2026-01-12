package ex5.RegExPatterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ex5.RegExPatterns.GeneralRegEx.*;

public class IfRegEx {
    private static final String IF_DECLARATION_LINE =
            SPACES_INITIAL //Line can start with spaces
                    + "(?:if|while)" + SPACE_CHAR + "*" //'if' keyword may have spaces after it
                    + "\\(" //Opening parenthesis
                    + "([^)]+)\\)" //Condition
                    + SPACE_CHAR + "*" //There could be spaces between the condition and parentheses
                    + "\\{" //The body of the if statement starts with a curly bracket
                    + SPACES_ENDING; //Line can end with spaces
    /*
     * Relevant to the statement above:
     * Group 1 captures the condition of the if statement.
     */

    /**
     * This pattern should be used to validate the title of an if statement.
     */
    public static final Pattern ifPattern = Pattern.compile(IF_DECLARATION_LINE);
}
