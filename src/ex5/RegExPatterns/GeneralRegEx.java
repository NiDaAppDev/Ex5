package ex5.RegExPatterns;

public class GeneralRegEx {

    public static final String OR = "|";
    public static final String SPACE_CHAR = "\\s";
    public static final String SPACES_INITIAL = "^" + SPACE_CHAR + "*";
    public static final String SPACES_ENDING = SPACE_CHAR + "*$";
    public static final String ENDING_SEMICOLON = SPACE_CHAR + "*;" + SPACE_CHAR + "*$";
    public static final String CODE_BLOCK_CLOSING_BRACKET = SPACE_CHAR + "}" + SPACES_ENDING;
}
