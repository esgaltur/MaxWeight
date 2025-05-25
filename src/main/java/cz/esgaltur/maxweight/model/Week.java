package cz.esgaltur.maxweight.model;

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