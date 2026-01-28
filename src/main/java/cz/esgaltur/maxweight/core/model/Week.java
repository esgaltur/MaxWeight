package cz.esgaltur.maxweight.core.model;

/**
 * Enum representing the weeks in the training program.
 * This replaces the string constants like "Week1", "Week2", etc.
 */
public enum Week {
    WEEK1(1),
    WEEK2(2),
    WEEK3(3),
    WEEK4(4),
    WEEK5(5),
    WEEK6(6);

    private final int weekNumber;

    Week(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    /**
     * Determine if a week number exists in the enum.
     *
     * @param weekNumber The week number to check
     * @return true if the week number is defined
     */
    public static boolean isValidWeekNumber(int weekNumber) {
        for (Week week : values()) {
            if (week.getWeekNumber() == weekNumber) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the minimum week number supported by the enum.
     *
     * @return The minimum week number
     */
    public static int minWeekNumber() {
        int min = Integer.MAX_VALUE;
        for (Week week : values()) {
            min = Math.min(min, week.getWeekNumber());
        }
        return min == Integer.MAX_VALUE ? 1 : min;
    }

    /**
     * Get the maximum week number supported by the enum.
     *
     * @return The maximum week number
     */
    public static int maxWeekNumber() {
        int max = Integer.MIN_VALUE;
        for (Week week : values()) {
            max = Math.max(max, week.getWeekNumber());
        }
        return max == Integer.MIN_VALUE ? 1 : max;
    }

    /**
     * Get a Week enum value from a week number
     * 
     * @param weekNumber The week number (1-6)
     * @return The corresponding Week enum value
     * @throws IllegalArgumentException if the week number is invalid
     */
    public static Week fromWeekNumber(int weekNumber) {
        for (Week week : values()) {
            if (week.getWeekNumber() == weekNumber) {
                return week;
            }
        }
        throw new IllegalArgumentException("Invalid week number: " + weekNumber);
    }

    /**
     * Returns the legacy key format used in the original code
     * 
     * @return A string in the format "Week1", "Week2", etc.
     */
    public String toLegacyKey() {
        return "Week" + weekNumber;
    }

    /**
     * Returns the legacy count key format used in the original code
     * 
     * @return A string in the format "CountW1", "CountW2", etc.
     */
    public String toLegacyCountKey() {
        return "CountW" + weekNumber;
    }
}
