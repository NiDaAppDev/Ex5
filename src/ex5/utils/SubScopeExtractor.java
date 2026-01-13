package ex5.utils;

import ex5.utils.LineReader.LINE_TYPE;

import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.GeneralRegExPatterns.CLOSING_BRACKET_PATTERN;

public class SubScopeExtractor {

    public static int getBlockEndIndex(String[] codeLines) {
        int remainingBracketsToCloseCount = 0;
        if(codeLines.length == 0) {
            return 0;
        }
        int innerIndex = 0;
        for (String codeLine : codeLines) {
            LINE_TYPE currentLineType = LineReader.classifyLine(codeLine);
            switch (currentLineType) {
                case METHOD_DEF, IF_OR_WHILE -> remainingBracketsToCloseCount++;
                default -> {
                    Matcher closingBracketM = CLOSING_BRACKET_PATTERN.matcher(codeLine);
                    if (closingBracketM.matches()) {
                        if (remainingBracketsToCloseCount == 0) {
                            return innerIndex;
                        }
                        remainingBracketsToCloseCount--;
                    }
                }
            }
            innerIndex += codeLine.length();
        }
        return 0;
    }

}
