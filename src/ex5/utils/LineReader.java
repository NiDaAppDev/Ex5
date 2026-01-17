package ex5.utils;

import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.IgnoreLineRegExPatterns.*;
import static ex5.reg_ex_patterns.VariableRegExPatterns.*;
import static ex5.reg_ex_patterns.IfWhileRegExPatterns.*;
import static ex5.reg_ex_patterns.MethodRegExPatterns.*;


public class LineReader {

    public enum LINE_TYPE {
        IGNORE,
        VAR_DEF,
        VAR_ASSIGN,
        IF_WHILE,
        METHOD_DEF,
        METHOD_CALL,
        RETURN,
        ILLEGAL
    }

    public static LineAnalysis classifyLine(String line){
        String[] current_groups = new String[0];
        Matcher ignoreM = IGNORE_LINE_PATTERN.matcher(line);
        if (ignoreM.matches()) {
            current_groups = new String[ignoreM.groupCount() + 1];
            for(int i = 0; i <= ignoreM.groupCount(); i++){
                current_groups[i] = ignoreM.group(i);
            }
            return new LineAnalysis(LineReader.LINE_TYPE.IGNORE, current_groups);
        }
        Matcher varDeclarationM = VARIABLE_DECLARATION_PATTERN.matcher(line);
        if (varDeclarationM.matches()) {
            current_groups = new String[varDeclarationM.groupCount() + 1];
            for(int i = 0; i <= varDeclarationM.groupCount(); i++){
                current_groups[i] = varDeclarationM.group(i);
            }
            return new LineAnalysis(LINE_TYPE.VAR_DEF, current_groups);
        }
        Matcher varAssignmentM = ASSIGN_VALUE_TO_VARIABLE_PATTERN.matcher(line);
        if (varAssignmentM.matches()) {
            current_groups = new String[varAssignmentM.groupCount() + 1];
            for(int i = 0; i <= varAssignmentM.groupCount(); i++){
                current_groups[i] = varAssignmentM.group(i);
            }
            return new LineAnalysis(LINE_TYPE.VAR_ASSIGN, current_groups);
        }
        Matcher ifWhileM = IF_WHILE_PATTERN.matcher(line);
        if (ifWhileM.matches()) {
            current_groups = new String[ifWhileM.groupCount() + 1];
            for(int i = 0; i <= ifWhileM.groupCount(); i++){
                current_groups[i] = ifWhileM.group(i);
            }
            return new LineAnalysis(LINE_TYPE.IF_WHILE, current_groups);
        }
        Matcher methodSignatureM = METHOD_PATTERN.matcher(line);
        if (methodSignatureM.matches()) {
            current_groups = new String[methodSignatureM.groupCount() + 1];
            for(int i = 0; i <= methodSignatureM.groupCount(); i++){
                current_groups[i] = methodSignatureM.group(i);
            }
            return new LineAnalysis(LINE_TYPE.METHOD_DEF, current_groups);
        }
        Matcher methodCallM = METHOD_CALL_PATTERN.matcher(line);
        if (methodCallM.matches()) {
            current_groups = new String[methodCallM.groupCount() + 1];
            for(int i = 0; i <= methodCallM.groupCount(); i++){
                current_groups[i] = methodCallM.group(i);
            }
            return new LineAnalysis(LINE_TYPE.METHOD_CALL, current_groups);
        }
        Matcher returnM = RETURN_STATEMENT_PATTERN.matcher(line);
        if (returnM.matches()) {
            current_groups = new String[returnM.groupCount() + 1];
            for(int i = 0; i <= returnM.groupCount(); i++){
                current_groups[i] = returnM.group(i);
            }
            return new LineAnalysis(LINE_TYPE.RETURN, current_groups);
        }

        return new LineAnalysis(LINE_TYPE.ILLEGAL, current_groups);
    }

}
