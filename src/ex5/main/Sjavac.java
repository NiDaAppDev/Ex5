package ex5.main;

import ex5.components.OUTPUTS;
import ex5.components.Scope;
import ex5.IllegalException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Sjavac {


    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentNumberException();
            }
            String sourceName = args[0];
            if(!sourceName.endsWith(".sjava")) {
                throw new WrongFileFormatException();
            }
            List<String> lines = Files.readAllLines(Paths.get(sourceName));
            String[] codeBlock = lines.toArray(new String[0]);

            Scope globalScope = new Scope(codeBlock);
            globalScope.parseCodeBlock();
            System.out.println(OUTPUTS.LEGAL.ordinal());
        } catch (IllegalArgumentNumberException | WrongFileFormatException | IOException e) {
            System.out.println(OUTPUTS.IOError.ordinal());
            System.err.println(e.getMessage());
        } catch (IllegalException e) {
            System.out.println(OUTPUTS.ILLEGAL.ordinal());
            System.err.println(e.getMessage());
        }
    }

}
