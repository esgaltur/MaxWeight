package cz.esgaltur.maxweight;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containing weight percentages and repetition counts for the bench press program
 * based on Professor Yuri Verkhoshansky's methodology.
 */
public class WeightsCounts {
    /**
     * Map storing weight percentages for each week.
     * Key format: "Week1" through "Week6"
     * Value: 2D array where [0] = day 1 exercises, [1] = day 2 exercises
     */
    private final Map<String, int[][]> weeks = new HashMap<>();

    /**
     * Map storing repetition counts for each week.
     * Key format: "CountW1" through "CountW6"
     * Value: 2D array where [0] = day 1 repetitions, [1] = day 2 repetitions
     */
    private final Map<String, int[][]> countsOfWeeks = new HashMap<>();

    /**
     * Constructor initializes the weight percentages and repetition counts for all weeks
     */
    public WeightsCounts() {
        // Initialize weight percentages for each week
        weeks.put("Week1",
            new int[][]{
                {45, 55, 65, 70, 70, 70},
                {45, 55, 65, 75, 80, 80, 80, 75, 65, 55}
            });
        weeks.put("Week2",
            new int[][]{
                {45, 55, 65, 70, 75, 75},
                {45, 55, 65, 75, 80, 85, 85, 85, 80, 70}
            });
        weeks.put("Week3",
            new int[][]{
                {45, 55, 70, 75, 80, 85},
                {45, 55, 65, 75, 85, 90, 90, 80, 60}
            });
        weeks.put("Week4",
            new int[][]{
                {45, 55, 65, 75, 85, 85},
                {45, 55, 65, 75, 85, 90, 95, 75}
            });
        weeks.put("Week5",
            new int[][]{
                {45, 55, 65, 75, 75},
                {45, 55, 65, 75, 80, 85}
            });
        weeks.put("Week6",
            new int[][]{
                {45, 55, 65, 75, 85},
                {45, 55, 65, 75, 85}
            });

        // Initialize repetition counts for each week
        countsOfWeeks.put("CountW1",
            new int[][]{
                {10, 8, 6, 6, 6, 6},
                {10, 8, 5, 5, 5, 5, 5, 5, 8, 12}
            });
        countsOfWeeks.put("CountW2",
            new int[][]{
                {10, 8, 6, 5, 5, 5},
                {10, 8, 5, 4, 4, 4, 4, 4, 5, 8}
            });
        countsOfWeeks.put("CountW3",
            new int[][]{
                {10, 8, 4, 3, 3, 3},
                {10, 8, 5, 4, 3, 3, 3, 5, 10}
            });
        countsOfWeeks.put("CountW4",
            new int[][]{
                {10, 8, 5, 4, 3, 3},
                {10, 8, 5, 4, 2, 2, 2, 6}
            });
        countsOfWeeks.put("CountW5",
            new int[][]{
                {10, 8, 5, 5, 5, 5},
                {10, 8, 5, 3, 3, 2}
            });
        countsOfWeeks.put("CountW6",
            new int[][]{
                {10, 8, 5, 3, 2},
                {10, 8, 5, 3, 2}
            });
    }

    /**
     * Gets the weight percentages for a specific week
     * 
     * @param weekKey The week identifier (e.g., "Week1", "Week2", etc.)
     * @return A 2D array of weight percentages where [0] = day 1, [1] = day 2
     */
    public int[][] getWeek(String weekKey) {
        return weeks.get(weekKey);
    }

    /**
     * Gets the repetition counts for a specific week
     * 
     * @param countKey The count identifier (e.g., "CountW1", "CountW2", etc.)
     * @return A 2D array of repetition counts where [0] = day 1, [1] = day 2
     */
    public int[][] getCountsOfWeeks(String countKey) {
        return countsOfWeeks.get(countKey);
    }
}
