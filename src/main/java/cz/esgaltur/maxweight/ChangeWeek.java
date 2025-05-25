package cz.esgaltur.maxweight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for generating and displaying weight training programs
 */
public class ChangeWeek {

    private final WeightsCounts weightsCounts = new WeightsCounts();

    /**
     * Prints the program for a range of weeks
     * @param from Starting week number
     * @param to Ending week number
     * @param maxWeight Maximum weight for bench press
     */
    public void printProgramForAllWeeks(int from, int to, int maxWeight) {
        for (int week = from; week <= to; week++) {
            String weekKey = "Week" + week;
            String countKey = "CountW" + week;

            if (week == 5 || week == 6) {
                printProgram(weekKey, countKey, maxWeight);
            } else {
                printProgramWithExtraExercises(weekKey, countKey, maxWeight);
            }
        }
    }

    /**
     * Prints the program for a specific week with extra exercises
     * @param weekKey Key for the week data
     * @param countKey Key for the count data
     * @param maxWeight Maximum weight for bench press
     */
    public void printProgramWithExtraExercises(String weekKey, String countKey, int maxWeight) {
        printProgramHeader(weekKey);

        int len = weightsCounts.getWeek(weekKey)[0].length;
        boolean isDay1 = true;

        // Print alternating day 1 and day 2 exercises
        for (int j = 0; j < len; j++) {
            printExerciseLine(weekKey, countKey, maxWeight, isDay1 ? 0 : 1, j, isDay1);
            isDay1 = !isDay1;
        }

        // Print extra exercises for day 2
        for (int j = 6; j < weightsCounts.getWeek(weekKey)[1].length; j++) {
            printExerciseLine(weekKey, countKey, maxWeight, 1, j, false);
        }
    }

    /**
     * Prints the program for a specific week
     * @param weekKey Key for the week data
     * @param countKey Key for the count data
     * @param maxWeight Maximum weight for bench press
     */
    public void printProgram(String weekKey, String countKey, int maxWeight) {
        printProgramHeader(weekKey);

        int len = weightsCounts.getWeek(weekKey)[0].length;
        boolean isDay1 = true;

        // Print alternating day 1 and day 2 exercises
        for (int j = 0; j < len; j++) {
            printExerciseLine(weekKey, countKey, maxWeight, isDay1 ? 0 : 1, j, isDay1);
            isDay1 = !isDay1;
        }
    }

    /**
     * Prints the header for a program week
     * @param weekKey Key for the week data
     */
    private void printProgramHeader(String weekKey) {
        System.out.println("***********" + weekKey + "***********");
        System.out.println("Day 1                  Day 2");
    }

    /**
     * Prints a single exercise line
     * @param weekKey Key for the week data
     * @param countKey Key for the count data
     * @param maxWeight Maximum weight for bench press
     * @param day Day index (0 for day 1, 1 for day 2)
     * @param exerciseIndex Index of the exercise
     * @param isDay1 Whether this is a day 1 exercise
     */
    private void printExerciseLine(String weekKey, String countKey, int maxWeight, int day, int exerciseIndex, boolean isDay1) {
        if (!isDay1) {
            System.out.print("                 * ");
        }

        int percent = weightsCounts.getWeek(weekKey)[day][exerciseIndex];
        double weight = calculatePercent(maxWeight, percent);
        int reps = weightsCounts.getCountsOfWeeks(countKey)[day][exerciseIndex];

        System.out.print(percent + "% - ");
        System.out.print(weight + " x ");
        System.out.print(reps);

        if (isDay1) {
            System.out.print("\t");
        } else {
            System.out.println();
        }
    }

    /**
     * Interactive menu for the program
     * @throws IOException If there's an error reading from the console
     */
    public void menu() throws IOException {
        int maxWeight = 0;
        int from = -1;
        int to = -1;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.println("******Program of calculate weight of bench press******");
                System.out.println("Please choose week from 1 to 6:");
                System.out.println("From: ");
                from = Integer.parseInt(reader.readLine());
                System.out.println("To:");
                to = Integer.parseInt(reader.readLine());

                if (from < 0 || from > 6 || to < 0 || to > 6) {
                    System.out.println("Error: Please enter numbers between 1 and 6");
                    continue;
                }

                if (from == 0 && to == 0) {
                    System.out.println("*****Thanks for using my program****");
                    break;
                }

                System.out.println("Enter please your max weight for bench press");
                maxWeight = Integer.parseInt(reader.readLine());
                printProgramForAllWeeks(from, to, maxWeight);
            } catch (NumberFormatException e) {
                System.out.println("You have entered a word or letter or empty string");
            }
        }
    }

    /**
     * Calculates the weight based on a percentage of the maximum weight
     * @param weight Maximum weight
     * @param percent Percentage to calculate
     * @return The calculated weight
     */
    public double calculatePercent(int weight, int percent) {
        return (weight * percent) / 100.0;
    }
}
