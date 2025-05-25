package cz.esgaltur.maxweight.model;

/**
 * Represents a single exercise in the training program.
 * Encapsulates the weight percentage, actual weight, and repetition count.
 */
public class Exercise {
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
     * Factory method to create an exercise from percentage, max weight, repetitions, and day
     * 
     * @param percentage The percentage of the maximum weight
     * @param maxWeight The maximum weight
     * @param repetitions The number of repetitions
     * @param day The day this exercise belongs to
     * @return A new Exercise instance
     */
    public static Exercise create(int percentage, int maxWeight, int repetitions, Day day) {
        double actualWeight = calculateWeight(maxWeight, percentage);
        return new Exercise(percentage, actualWeight, repetitions, day);
    }

    /**
     * Calculate the actual weight based on a percentage of the maximum weight
     * 
     * @param maxWeight The maximum weight
     * @param percentage The percentage to calculate
     * @return The calculated weight
     */
    private static double calculateWeight(int maxWeight, int percentage) {
        return (maxWeight * percentage) / 100.0;
    }
}