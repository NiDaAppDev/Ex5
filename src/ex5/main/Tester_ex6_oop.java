package ex5.main;  // <--- THIS LINE IS CRITICAL. IT MUST MATCH THE FOLDER.

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Tester_ex6_oop {
    // UPDATED PATHS to match your specific structure
    private static Path pathToFiles = Paths.get("src", "tester_files");
    private static Path pathToTests = Paths.get("C:\\Users\\tomer\\Desktop\\The Science of Computers\\java\\Ex5\\src\\tester_files\\tests");
    private static Path pathToUserTests = Paths.get(pathToTests.toString(), "specificTests");
    private static Path pathToOutputFile = Paths.get(pathToFiles.toString(), "user_output.txt");
    private static Path pathToSchoolSolution = Paths.get(pathToFiles.toString(), "school_solution.jar");

    private static PrintStream originalOutStream = System.out;
    private static PrintStream originalErrStream = System.err;
    private static ByteArrayOutputStream baosP = new ByteArrayOutputStream();
    private static ByteArrayOutputStream baosE = new ByteArrayOutputStream();
    private static PrintStream printsRecorder = new PrintStream(baosP);
    private static PrintStream ErrorsRecorder = new PrintStream(baosE);
    private static String error = "Error placeholder"; // Shortened for brevity

    @Test
    public void runSpecificTests_optional() {
        int i = 1;
        LinkedList<File> listOfTests = getListOfTests(pathToUserTests);
        if (listOfTests.size() == 0) {
            System.out.println("Place test files in 'tester_files/tests/specificTests' to use this.");
        }
        for (File file : listOfTests) {
            Path testPath = Paths.get(file.getPath());
            System.out.printf("\nstarting test %s:\n", file.getName());

            String[] userPrints = runTestWithOnUser(testPath.toString());
            String[] schoolPrints = runTestWithSchoolSolution(testPath.toString());

            System.out.println("--- School Solution ---");
            System.out.println(schoolPrints[0]);
            System.out.println(schoolPrints[1]);
            System.out.println("--- Your Solution ---");
            System.out.println(userPrints[0]);
            System.out.println(userPrints[1]);
        }
    }

    @Test
    public void runAllTests() {
        resetOutputFile();
        System.out.println("Running all tests... results in user_output.txt");
        boolean passedAll = true;
        int numOfPassed = 0;
        LinkedList<File> listOfTests = getListOfTests(pathToTests);

        for (File file : listOfTests) {
            if (doOneTest(Paths.get(file.getPath()))) numOfPassed++;
            else passedAll = false;
        }

        String summary = String.format("\nPassed %d out of %d tests.\n", numOfPassed, listOfTests.size());
        System.out.println(summary);
        writeToFile(summary);
        assertTrue(error, passedAll);
    }

    @Test
    public void runType2ErrorTests() {
        System.out.println("\nstarting runType2ErrorTests:");
        // Using existing tests if available, otherwise just checking IO logic
        String dummyPath = Paths.get(pathToTests.toString(), "dummy.sjava").toString();
        String[] args = {dummyPath + " extraArg", "2 args test"};

        try {
            String[] userPrints = runTestWithOnUser(args[0]);
            // We expect "2"
            if (userPrints[0].trim().equals("2")) {
                System.out.println("Passed: Output was 2 as expected.");
            } else {
                System.out.println("Failed: Expected 2, got " + userPrints[0]);
                fail("Expected 2 for IO error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    // --- Helpers ---

    private LinkedList<File> getListOfTests(Path testsPath) {
        LinkedList<File> listOfTests = new LinkedList<>();
        File folder = new File(testsPath.toString());

        // 1. Get files in current folder
        File[] files = folder.listFiles();
        if (files == null) return listOfTests;

        for (File f : files) {
            // If it's a file, add it
            if (f.isFile()) {
                listOfTests.add(f);
            }
            // If it's a folder (like "SchoolTests"), look inside it!
            else if (f.isDirectory()) {
                listOfTests.addAll(getListOfTests(f.toPath()));
            }
        }
        return listOfTests;
    }

    private boolean doOneTest(Path pathToTest) {
        String[] schoolOutput = runTestWithSchoolSolution(pathToTest.toString());
        String[] userOutput = runTestWithOnUser(pathToTest.toString());

        if (userOutput[0].length() != 1) {
            String toPrint = "Invalid output for " + pathToTest.getFileName() + ": " + userOutput[0];
            System.err.println(toPrint);
            writeToFile(toPrint + "\n");
            return false;
        }

        boolean passed = schoolOutput[0].equals(userOutput[0]);
        writeResultToFile(pathToTest, schoolOutput[0], userOutput[0], passed);
        return passed;
    }

    private void resetOutputFile() {
        try (PrintWriter writer = new PrintWriter(pathToOutputFile.toString())) {
            writer.print("Log started\n\n");
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }

    private void writeResultToFile(Path path, String school, String user, boolean passed) {
        String line = String.format("%s | School: %s | User: %s | %s",
                passed ? "PASS" : "FAIL", school, user, path.getFileName());
        writeToFile(line);
    }

    private void writeToFile(String text) {
        try (FileWriter fw = new FileWriter(pathToOutputFile.toString(), true);
             PrintWriter out = new PrintWriter(fw)) {
            out.println(text);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private String[] runTestWithOnUser(String path) {
        recordPrints();
        try {
            // DIRECT CALL TO YOUR MAIN CLASS
            Sjavac.main(new String[]{path});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getPrintsAndBackToNormal();
    }

    private String[] runTestWithSchoolSolution(String path) {
        return excCommand("java", "-jar", pathToSchoolSolution.toString(), path);
    }

    private String[] excCommand(String... args) {
        String[] records = new String[2];
        try {
            Process p = new ProcessBuilder(args).start();
            BufferedReader out = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            records[0] = out.lines().collect(Collectors.joining());
            records[1] = err.lines().collect(Collectors.joining());
        } catch (IOException e) { return new String[]{"error", "error"}; }
        return records;
    }

    private static void recordPrints() {
        System.setOut(printsRecorder);
        System.setErr(ErrorsRecorder);
    }

    private static String[] getPrintsAndBackToNormal() {
        String[] records = {baosP.toString().trim(), baosE.toString().trim()};
        baosP.reset(); baosE.reset();
        System.setOut(originalOutStream);
        System.setErr(originalErrStream);
        return records;
    }
}