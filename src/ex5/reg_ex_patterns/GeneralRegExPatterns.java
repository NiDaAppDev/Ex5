package ex5.reg_ex_patterns;

import java.util.regex.Pattern;

/**
 * This class holds the general regex used over the project.
 */
public class GeneralRegExPatterns {

    /**
     * or regex operator
     */
    public static final String OR = "|";
    /**
     * space regex
     */
    public static final String SPACE_CHAR = "\\s";
    /**
     * spaces initial regex
     */
    public static final String SPACES_INITIAL = "^" + SPACE_CHAR + "*";
    /**
     * spaces ending regex
     */
    public static final String SPACES_ENDING = SPACE_CHAR + "*$";
    /**
     * ending semicolon regex
     */
    public static final String ENDING_SEMICOLON = SPACE_CHAR + "*;" + SPACE_CHAR + "*$";
    /**
     * codeBlock closing bracket regex
     */
    public static final String CODE_BLOCK_CLOSING_BRACKET = SPACES_INITIAL + "}" + SPACES_ENDING;

    /**
     * A regex pattern to validate a codeBlock closing bracket
     */
    public static final Pattern CLOSING_BRACKET_PATTERN = Pattern.compile(CODE_BLOCK_CLOSING_BRACKET);
}
