package org.example;

//import org.example.Solution.Walk;
//import org.example.Tester.Walk;
import org.junit.jupiter.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.RepetitionInfo;

import static org.junit.jupiter.api.Assertions.*;

class WalkTest {
    private static final XYSeries expectedExecutionTimesSeries = new XYSeries("Expected Execution Times");
    private static final XYSeries actualExecutionTimesSeries = new XYSeries("Actual Execution Times");

    // Add these variables as class members in the WalkTest class
    private static long totalExpectedExecutionTime = 0;
    private static long totalActualExecutionTime = 0;


    //========================================================================================
    private static final int TOTAL_TEST_CASES = 200; // Set the total number of test cases

    //========================================================================================

    /**
     * In `@RepeatedTest(value = TOTAL_TEST_CASES, name = "Run {currentRepetition} of {totalRepetitions}")`:
     * Change the 100 in private static final int TOTAL_TEST_CASES = 100;` to the number of times you want to run the test.
     * */
    @RepeatedTest(value =  TOTAL_TEST_CASES, name = "Run {currentRepetition} of {totalRepetitions}")
    @DisplayName("Testing the main method")
    public void testMainMethod(RepetitionInfo repetitionInfo) {

        Logger LOGGER = LoggerFactory.getLogger(WalkTest.class);

        int repetitionCount = repetitionInfo.getCurrentRepetition();

        InputGenerator inputGenerator = new InputGenerator();


        //===================================================================================================================================================
        /**
         * Method format:
         * `generateInputWithRandomValues(int repetitionCount, int n, int m, int kRange)`
         *
         * 1. If you don't want the test case to increase during Repeated Tests:
         *  - Change repetitionCount to 0.
         *
         * 2. If you want to change number of column (x):
         *  - Change n to your desired number.
         *
         * 3. If you want to change number of rows (y):
         *  - Change m to your desired number.
         *
         * 4. If you want to change jump count (k):
         *  - Change kRange to your desired number.
         *
         * */
        String input = inputGenerator.generateInputWithRandomValues(repetitionCount, 6, 3, 5);

//        String input = inputGenerator.generateInputWithRandomValues(0, 150, 150, 10);  // example if you don't want test case to increase during Repeated Tests
//        String input = inputGenerator.generateFixedInput();   // uncomment if want use hard-code input

        //===================================================================================================================================================




        long expectedOutputStartTime = System.currentTimeMillis(); // Record start time for ExpectedOutputCalculator
        ExpectedOutputCalculator expectedOutputCalculator = new ExpectedOutputCalculator();
        String expectedOutput = expectedOutputCalculator.calculateExpectedOutput(input); // Replace with the expected output
        long expectedOutputEndTime = System.currentTimeMillis(); // Record end time for ExpectedOutputCalculator
        long expectedOutputExecutionTime = expectedOutputEndTime - expectedOutputStartTime; // Calculate execution time for ExpectedOutputCalculator

        long actualOutputStartTime = System.currentTimeMillis(); // Record start time for ActualOutputCalculator
        ActualOutputCalculator actualOutputCalculator = new ActualOutputCalculator();
        String actualOutput = actualOutputCalculator.calculateActualOutput(input);
        long actualOutputEndTime = System.currentTimeMillis(); // Record end time for ActualOutputCalculator
        long actualOutputExecutionTime = actualOutputEndTime - actualOutputStartTime; // Calculate execution time for ActualOutputCalculator

        // Inside the testMainMethod() after calculating execution times for expected and actual output
        // Accumulate total execution time for each output
        totalExpectedExecutionTime += expectedOutputExecutionTime;
        totalActualExecutionTime += actualOutputExecutionTime;

        // Record execution times for ExpectedOutputCalculator and ActualOutputCalculator
        expectedExecutionTimesSeries.add(repetitionInfo.getCurrentRepetition(), expectedOutputExecutionTime);
        actualExecutionTimesSeries.add(repetitionInfo.getCurrentRepetition(), actualOutputExecutionTime);

        // Log the test case number in gray color
        LOGGER.info("\u001B[90mTest Case: {}\u001B[0m", repetitionCount);//        LOGGER.info("\u001B[34mTest Case: \n{}\u001B[0m", input); // Blue color for input
        // Check if the expected output matches the actual output
        if (expectedOutput.equals(actualOutput)) {
            // Log in green color if expected output matches actual output
            LOGGER.info("\u001B[32mExpected Output: {}\u001B[0m", expectedOutput); // Green color for expected output
            LOGGER.info("\u001B[32mActual Output: {}\u001B[0m", actualOutput); // Green color for actual output

//            // Specify the directory where the file will be created
//            String directoryPath = "failed_test_case/";
//
//            // Write input data to a file
//            writeInputToFile(directoryPath, "failed_test_case_" + repetitionCount + ".txt", input);
        } else {
            // Log in red color if expected output doesn't match actual output
            LOGGER.info("\u001B[31mExpected Output: {}\u001B[0m", expectedOutput); // Red color for expected output
            LOGGER.info("\u001B[31mActual Output: {}\u001B[0m", actualOutput); // Red color for actual output

            // Specify the directory where the file will be created
            String directoryPath = "failed_test_case/";

            // Write input data to a file
            writeInputToFile(directoryPath, "failed_test_case_" + repetitionCount + ".txt", input);
        }
        LOGGER.info("\u001B[34mExpected Output Calculator Execution Time: {} milliseconds\u001B[0m", expectedOutputExecutionTime);
        LOGGER.info("\u001B[34mActual Output Calculator Execution Time: {} milliseconds\u001B[0m", actualOutputExecutionTime);

//        LOGGER.info("\u001B[90mInput: {}\u001B[0m", input);//        LOGGER.info("\u001B[34mTest Case: \n{}\u001B[0m", input); // Blue color for input

        // Compare against expected output
        assertEquals(expectedOutput, actualOutput);
    }

    // Method to write input data to a file in a specified directory
    private void writeInputToFile(String directoryPath, String filename, String input) {
        try {
            Path directory = Paths.get(directoryPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            File file = new File(directoryPath + filename);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(input);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create and save the line graph
    private static void saveLineGraph() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(expectedExecutionTimesSeries);
        dataset.addSeries(actualExecutionTimesSeries);

        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "Time Complexity for Each Test Run",
                "Test Run",
                "Execution Time (milliseconds)",
                dataset
        );

        try {
            ChartUtils.saveChartAsJPEG(new File("line_graph.jpg"), lineChart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void generateLineGraph() {
        saveLineGraph();

        int totalTestCases = TOTAL_TEST_CASES; // Change this to the total number of test cases

        // Calculate average execution times
        double averageExpectedTime = (double) totalExpectedExecutionTime / totalTestCases;
        double averageActualTime = (double) totalActualExecutionTime / totalTestCases;

        // Print average times
        System.out.println("Average Expected Execution Time: " + averageExpectedTime + " milliseconds");
        System.out.println("Average Actual Execution Time: " + averageActualTime + " milliseconds");
    }

}

class ExpectedOutputCalculator {
    public static String calculateExpectedOutput(String input) {
        String expectedOutput; // Initialize the actualOutput variable

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        try {
            // input stream
            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setIn(inputStream);  // set input stream
            System.setOut(new PrintStream(outputStream));

            org.example.Solution.Walk solutionWalk = new org.example.Solution.Walk();
            solutionWalk.main(new String[]{});

            // Capture the output
            expectedOutput = outputStream.toString().trim();

        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        // Return the calculated expected output as a string
        return expectedOutput; // Replace with your calculation
    }
}

class ActualOutputCalculator{
    public static String calculateActualOutput(String input) {
        String actualOutput;

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        try {
            // input stream
            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setIn(inputStream);  // set input stream
            System.setOut(new PrintStream(outputStream));

            org.example.Tester.Walk testerWalk = new org.example.Tester.Walk();

            // Run your main method
            testerWalk.main(new String[]{});

            // Capture the output
            actualOutput = outputStream.toString().trim();

        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        // Return the calculated expected output as a string
        return actualOutput; // Replace with your calculation
    }
}

class InputGenerator {
    public static String generateInputWithRandomValues(int repetitionCount, int n, int m, int kRange) {
        // Generate random input for testing
        n = n + repetitionCount; // Define your grid dimensions
        m = m + repetitionCount;
        kRange = kRange + repetitionCount;

        int s1 = getRandomValue(n);
        int s2 = getRandomValue(m);
        int e1 = getRandomValue(n);
        int e2 = getRandomValue(m);
        int k = getRandomKValue(kRange);
        int[][] grid = generateRandomGrid(n, m, s1, s2, e1, e2);

        // Prepare input string
        StringBuilder input = new StringBuilder();
        input.append(n).append(" ").append(m).append("\n")
                .append("(").append(s1).append(", ").append(s2).append(") (").append(e1).append(", ").append(e2).append(")\n")
                .append(k).append("\n");

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                input.append(grid[i][j]).append(" ");
            }
            input.append("\n");
        }

        return input.toString();
    }

    public static String generateFixedInput() {
        // Prepare fixed input string
        return "6 3\n" +
                "(3, 1) (5, 1)\n" +
                "0\n" +
                "0 1 0 1 0 1\n" +
                "0 0 0 0 0 1\n" +
                "0 1 0 0 0 0\n";
    }

    // Method to generate random test cases
    private static int[][] generateRandomGrid(int n, int m, int s1, int s2, int e1, int e2) {
        int[][] grid = new int[m][n];
        Random random = new Random();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = random.nextInt(2); // Assign either 0 or 1 randomly
            }
        }

        // Set the coordinates s1, s2, e1, e2 to 0
        grid[s2 - 1][s1 - 1] = 0;
        grid[e2 - 1][e1 - 1] = 0;

        return grid;
    }

    // Method to generate a random value for k
    private static int getRandomKValue(int k) {
        Random random = new Random();
        return random.nextInt(k); // Change the upper limit according to your requirement
    }

    // Method to get a random value within the range [1, max]
    private static int getRandomValue(int max) {
        Random random = new Random();
        return random.nextInt(max) + 1;
    }

}
