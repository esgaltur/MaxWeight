package cz.esgaltur.maxweight.service;

import cz.esgaltur.maxweight.model.Day;
import cz.esgaltur.maxweight.model.Week;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

/**
 * Service class providing weight percentages and repetition counts for each week and day.
 * This replaces the WeightsCounts class with a cleaner interface.
 */
@Service
public class ProgramDataService {
    private final Map<Week, int[][]> weightPercentages;
    private final Map<Week, int[][]> repetitionCounts;

    /**
     * Constructor initializes the weight percentages and repetition counts for all weeks
     */
    public ProgramDataService() {
        weightPercentages = new EnumMap<>(Week.class);
        repetitionCounts = new EnumMap<>(Week.class);

        initializeWeightPercentages();
        initializeRepetitionCounts();
    }

    /**
     * Initialize the weight percentages for each week
     */
    private void initializeWeightPercentages() {
        weightPercentages.put(Week.WEEK1,
            new int[][]{
                {45, 55, 65, 70, 70, 70},
                {45, 55, 65, 75, 80, 80, 80, 75, 65, 55}
            });
        weightPercentages.put(Week.WEEK2,
            new int[][]{
                {45, 55, 65, 70, 75, 75},
                {45, 55, 65, 75, 80, 85, 85, 85, 80, 70}
            });
        weightPercentages.put(Week.WEEK3,
            new int[][]{
                {45, 55, 70, 75, 80, 85},
                {45, 55, 65, 75, 85, 90, 90, 80, 60}
            });
        weightPercentages.put(Week.WEEK4,
            new int[][]{
                {45, 55, 65, 75, 85, 85},
                {45, 55, 65, 75, 85, 90, 95, 75}
            });
        weightPercentages.put(Week.WEEK5,
            new int[][]{
                {45, 55, 65, 75, 75},
                {45, 55, 65, 75, 80, 85}
            });
        weightPercentages.put(Week.WEEK6,
            new int[][]{
                {45, 55, 65, 75, 85},
                {45, 55, 65, 75, 85}
            });
    }

    /**
     * Initialize the repetition counts for each week
     */
    private void initializeRepetitionCounts() {
        repetitionCounts.put(Week.WEEK1,
            new int[][]{
                {10, 8, 6, 6, 6, 6},
                {10, 8, 5, 5, 5, 5, 5, 5, 8, 12}
            });
        repetitionCounts.put(Week.WEEK2,
            new int[][]{
                {10, 8, 6, 5, 5, 5},
                {10, 8, 5, 4, 4, 4, 4, 4, 5, 8}
            });
        repetitionCounts.put(Week.WEEK3,
            new int[][]{
                {10, 8, 4, 3, 3, 3},
                {10, 8, 5, 4, 3, 3, 3, 5, 10}
            });
        repetitionCounts.put(Week.WEEK4,
            new int[][]{
                {10, 8, 5, 4, 3, 3},
                {10, 8, 5, 4, 2, 2, 2, 6}
            });
        repetitionCounts.put(Week.WEEK5,
            new int[][]{
                {10, 8, 5, 5, 5},
                {10, 8, 5, 3, 3, 2}
            });
        repetitionCounts.put(Week.WEEK6,
            new int[][]{
                {10, 8, 5, 3, 2},
                {10, 8, 5, 3, 2}
            });
    }

    /**
     * Get the weight percentages for a specific week and day
     * 
     * @param week The week
     * @param day The day
     * @return An array of weight percentages
     */
    public int[] getWeightPercentages(Week week, Day day) {
        return weightPercentages.get(week)[day.getIndex()];
    }

    /**
     * Get the repetition counts for a specific week and day
     * 
     * @param week The week
     * @param day The day
     * @return An array of repetition counts
     */
    public int[] getRepetitionCounts(Week week, Day day) {
        return repetitionCounts.get(week)[day.getIndex()];
    }

    /**
     * Get the number of exercises for a specific week and day
     * 
     * @param week The week
     * @param day The day
     * @return The number of exercises
     */
    public int getExerciseCount(Week week, Day day) {
        return getWeightPercentages(week, day).length;
    }

    /**
     * Get the minimum number of exercises between day 1 and day 2 for a specific week
     * 
     * @param week The week
     * @return The minimum number of exercises
     */
    public int getMinExerciseCount(Week week) {
        return Math.min(
            getExerciseCount(week, Day.DAY1),
            getExerciseCount(week, Day.DAY2)
        );
    }
}
