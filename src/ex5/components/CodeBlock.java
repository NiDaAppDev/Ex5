package ex5.components;

public class CodeBlock {

    /*Keyword Constants*/

    //General
    private static final String SPACE_CHAR = "\\s";
    private static final String SPACES_INITIAL = "^" + SPACE_CHAR + "*";
    private static final String SPACES_ENDING = SPACE_CHAR + "*$";
    private static final String EMPTY_LINE = SPACES_INITIAL + "$";
    private static final String COMMENT_LINE = SPACES_INITIAL + "\\/\\/";
    private static final String ILLEGAL_ONE_LINE_COMMENT = "\\S+\\/\\/";
    private static final String ILLEGAL_MULTILINE_COMMENT_OPENER = "[^\\/]?\\/\\*";
    private static final String BLOCK_OPENER = "{";
    private static final String BLOCK_CLOSER = "}";
    private static final String CODE_LINE_END = ";";
    private static final String OPERATOR = "[+-\\*\\/]";
    private static final String ARRAY_BRACKET = "[\\[\\]]";

    //Variables
    private static final String INT_TYPE = "int";
    private static final String DOUBLE_TYPE = "double";
    private static final String STRING_TYPE = "String";
    private static final String BOOLEAN_TYPE = "boolean";
    private static final String CHAR_TYPE = "char";
    private static final String VAR_NAME = "\\b[a-zA-Z_][0-9a-zA-Z]\\w*\\b";
    //TODO: what if +-0?
    private static final String INT_VALUE = OPERATOR + "?\\d+";
    private static final String DOUBLE_VALUE = OPERATOR + "";


}
