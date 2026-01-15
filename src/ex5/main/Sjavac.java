package ex5.main;

import ex5.components.Scope;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Sjavac {


    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IOException();
            }
            String sourceName = args[0];

            List<String> lines = Files.readAllLines(Paths.get(sourceName));
            String[] codeBlock = lines.toArray(new String[0]);

            Scope globalScope = new Scope(codeBlock);
            globalScope.parseCodeBlock();
            System.out.println("0");
        } catch (IOException e) {
            System.out.println("2");
        } catch (Exception e) {
            System.out.println("1");
        }
    }
}