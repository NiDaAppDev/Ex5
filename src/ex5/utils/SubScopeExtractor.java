package ex5.utils;

import ex5.utils.LineReader.LINE_TYPE;

import java.util.Arrays;
import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.GeneralRegExPatterns.CLOSING_BRACKET_PATTERN;

/**
 * A helper that extracts the code of a sub-scope.
 */
public class SubScopeExtractor {

    /**
     * @param codeLines the lines to search the end of the block in.
     * @return the index of the end of the block.
     */
    public static int getBlockEndIndex(String[] codeLines) {
        int remainingBracketsToCloseCount = 0;
        for (int i = 0; i < codeLines.length; i++) {
            LINE_TYPE currentLineType = LineReader.classifyLine(codeLines[i]).getType();
            switch (currentLineType) {
                case METHOD_DEF, IF_WHILE -> remainingBracketsToCloseCount++;
                default -> {
                    Matcher closingBracketM = CLOSING_BRACKET_PATTERN.matcher(codeLines[i]);
                    if (closingBracketM.matches()) {
                        if (remainingBracketsToCloseCount == 0) {
                            return i;
                        }
                        remainingBracketsToCloseCount--;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * @param codeBlock the code block to extract the sub-scope's code block from.
     * @param startingIndex the index of the start of the sub-scope's code block.
     * @param endingIndex the index of the end of the sub-scope's code block.
     * @return the sub-scope's code block.
     */
    public static String[] getSubScopeCodeBlock(String[] codeBlock,
                                                int startingIndex,
                                                int endingIndex) {
        return Arrays.copyOfRange(codeBlock,
                startingIndex,
                endingIndex);
    }

}
