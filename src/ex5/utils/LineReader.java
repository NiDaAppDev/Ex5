package ex5.utils;

import java.util.regex.Matcher;

import static ex5.reg_ex_patterns.IgnoreLineRegExPatterns.*;
import static ex5.reg_ex_patterns.VariableRegExPatterns.*;
import static ex5.reg_ex_patterns.IfWhileRegExPatterns.*;
import static ex5.reg_ex_patterns.MethodRegExPatterns.*;


public class LineReader {

    private static String[] current_groups = new String[0];

    enum LINE_TYPE {
        IGNORE,
        VAR_DEF,
        VAR_ASSIGN,
        IF_OR_WHILE,
        METHOD_DEF,
        METHOD_CALL,
        ILLEGAL
    }

    public static LINE_TYPE classifyLine(String line){
        current_groups = new String[0];
        Matcher ignoreM = IGNORE_LINE_PATTERN.matcher(line);
        if (ignoreM.matches()) {
            current_groups = new String[ignoreM.groupCount()];
            for(int i = 0; i < ignoreM.groupCount(); i++){
                current_groups[i] = ignoreM.group(i);
            }
            return LINE_TYPE.IGNORE;
        }
        Matcher varDeclarationM = VARIABLE_DECLARATION_PATTERN.matcher(line);
        if (varDeclarationM.matches()) {
            current_groups = new String[varDeclarationM.groupCount()];
            for(int i = 0; i < varDeclarationM.groupCount(); i++){
                current_groups[i] = varDeclarationM.group(i);
            }
            return LINE_TYPE.VAR_DEF;
        }
        Matcher varAssignmentM = ASSIGN_VALUE_TO_VARIABLE_PATTERN.matcher(line);
        if (varAssignmentM.matches()) {
            current_groups = new String[varAssignmentM.groupCount()];
            for(int i = 0; i < varAssignmentM.groupCount(); i++){
                current_groups[i] = varAssignmentM.group(i);
            }
            return LINE_TYPE.VAR_ASSIGN;
        }
        Matcher ifWhileM = IF_WHILE_PATTERN.matcher(line);
        if (ifWhileM.matches()) {
            current_groups = new String[ifWhileM.groupCount()];
            for(int i = 0; i < ifWhileM.groupCount(); i++){
                current_groups[i] = ifWhileM.group(i);
            }
            return LINE_TYPE.IF_OR_WHILE;
        }
        Matcher methodSignatureM = METHOD_PATTERN.matcher(line);
        if (methodSignatureM.matches()) {
            current_groups = new String[methodSignatureM.groupCount()];
            for(int i = 0; i < methodSignatureM.groupCount(); i++){
                current_groups[i] = methodSignatureM.group(i);
            }
            return LINE_TYPE.METHOD_DEF;
        }
        Matcher methodCallM = METHOD_CALL_PATTERN.matcher(line);
        if (methodCallM.matches()) {
            current_groups = new String[methodCallM.groupCount()];
            for(int i = 0; i < methodCallM.groupCount(); i++){
                current_groups[i] = methodCallM.group(i);
            }
            return LINE_TYPE.METHOD_CALL;
        }
        return LINE_TYPE.ILLEGAL;
    }

    public static String[] getCurrentGroups() {
        return current_groups;
    }

}
