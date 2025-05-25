package cz.esgaltur.maxweight.core.model;

/**
 * Enum representing the training days in the program.
 * This replaces the hardcoded indices (0 and 1) used in the original code.
 */
public enum Day {
    DAY1(0, "Day 1"),
    DAY2(1, "Day 2");

    private final int index;
    private final String displayName;

    Day(int index, String displayName) {
        this.index = index;
        this.displayName = displayName;
    }

    /**
     * Get the array index corresponding to this day
     * 
     * @return The index (0 for DAY1, 1 for DAY2)
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get the display name for this day
     * 
     * @return The display name (e.g., "Day 1", "Day 2")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get a Day enum value from an index
     * 
     * @param index The index (0 for DAY1, 1 for DAY2)
     * @return The corresponding Day enum value
     * @throws IllegalArgumentException if the index is invalid
     */
    public static Day fromIndex(int index) {
        for (Day day : values()) {
            if (day.getIndex() == index) {
                return day;
            }
        }
        throw new IllegalArgumentException("Invalid day index: " + index);
    }
}