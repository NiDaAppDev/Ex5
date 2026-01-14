package ex5.utils;

import ex5.utils.LineReader.LINE_TYPE;

import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.GeneralRegExPatterns.CLOSING_BRACKET_PATTERN;

public class SubScopeExtractor {

    public static int getBlockEndIndex(String[] codeLines) {
        int remainingBracketsToCloseCount = 0;
        for (int i = 0; i < codeLines.length; i++) {
            LINE_TYPE currentLineType = LineReader.classifyLine(codeLines[i]);
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

}
