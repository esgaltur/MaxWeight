package cz.esgaltur.maxweight.core.model;

/**
 * Represents a single exercise in the training program.
 * Encapsulates the weight percentage, actual weight, and repetition count.
 */
public class Exercise {
    public static final double DEFAULT_ROUNDING_INCREMENT = 2.5;
    private final int weightPercentage;
    private final double actualWeight;
    private final int repetitions;
    private final Day day;

    /**
     * Constructor for creating an exercise
     * 
     * @param weightPercentage The percentage of the maximum weight
     * @param actualWeight The calculated actual weight
     * @param repetitions The number of repetitions
     * @param day The day this exercise belongs to
     */
    public Exercise(int weightPercentage, double actualWeight, int repetitions, Day day) {
        this.weightPercentage = weightPercentage;
        this.actualWeight = actualWeight;
        this.repetitions = repetitions;
        this.day = day;
    }

    /**
     * Get the weight percentage
     * 
     * @return The weight percentage
     */
    public int getWeightPercentage() {
        return weightPercentage;
    }

    /**
     * Get the actual weight
     * 
     * @return The actual weight
     */
    public double getActualWeight() {
        return actualWeight;
    }

    /**
     * Get the number of repetitions
     * 
     * @return The number of repetitions
     */
    public int getRepetitions() {
        return repetitions;
    }

    /**
     * Get the day this exercise belongs to
     * 
     * @return The day
     */
    public Day getDay() {
        return day;
    }

    /**
     * Factory method to create an exercise from percentage, max weight, repetitions, and day.
     * Rounds the calculated weight to {@link #DEFAULT_ROUNDING_INCREMENT}.
     * 
     * @param percentage The percentage of the maximum weight
     * @param maxWeight The maximum weight
     * @param repetitions The number of repetitions
     * @param day The day this exercise belongs to
     * @return A new Exercise instance
     */
    public static Exercise create(int percentage, int maxWeight, int repetitions, Day day) {
        double actualWeight = calculateWeight(maxWeight, percentage, DEFAULT_ROUNDING_INCREMENT);
        return new Exercise(percentage, actualWeight, repetitions, day);
    }

    /**
     * Factory method to create an exercise with a custom rounding increment.
     *
     * @param percentage The percentage of the maximum weight
     * @param maxWeight The maximum weight
     * @param repetitions The number of repetitions
     * @param day The day this exercise belongs to
     * @param roundingIncrement The rounding increment (e.g., 2.5 for kg plates)
     * @return A new Exercise instance
     */
    public static Exercise create(int percentage, int maxWeight, int repetitions, Day day, double roundingIncrement) {
        double actualWeight = calculateWeight(maxWeight, percentage, roundingIncrement);
        return new Exercise(percentage, actualWeight, repetitions, day);
    }

    /**
     * Calculate the actual weight based on a percentage of the maximum weight
     * 
     * @param maxWeight The maximum weight
     * @param percentage The percentage to calculate
     * @param roundingIncrement The rounding increment
     * @return The calculated weight
     */
    private static double calculateWeight(int maxWeight, int percentage, double roundingIncrement) {
        double rawWeight = (maxWeight * percentage) / 100.0;
        return roundToIncrement(rawWeight, roundingIncrement);
    }

    private static double roundToIncrement(double value, double increment) {
        if (increment <= 0) {
            return value;
        }
        return Math.round(value / increment) * increment;
    }
}
