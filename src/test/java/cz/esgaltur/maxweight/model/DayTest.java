package cz.esgaltur.maxweight.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Day enum.
 */
public class DayTest {

    /**
     * Test the getIndex method.
     */
    @Test
    public void testGetIndex() {
        assertEquals(0, Day.DAY1.getIndex());
        assertEquals(1, Day.DAY2.getIndex());
    }

    /**
     * Test the getDisplayName method.
     */
    @Test
    public void testGetDisplayName() {
        assertEquals("Day 1", Day.DAY1.getDisplayName());
        assertEquals("Day 2", Day.DAY2.getDisplayName());
    }

    /**
     * Test the fromIndex method with valid inputs.
     */
    @Test
    public void testFromIndexValid() {
        assertEquals(Day.DAY1, Day.fromIndex(0));
        assertEquals(Day.DAY2, Day.fromIndex(1));
    }

    /**
     * Test the fromIndex method with invalid inputs.
     */
    @Test
    public void testFromIndexInvalid() {
        // Test with index less than 0
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            Day.fromIndex(-1);
        });
        assertTrue(exception1.getMessage().contains("Invalid day index"));

        // Test with index greater than 1
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Day.fromIndex(2);
        });
        assertTrue(exception2.getMessage().contains("Invalid day index"));
    }

    /**
     * Test that the values method returns all enum values.
     */
    @Test
    public void testValues() {
        Day[] days = Day.values();
        assertEquals(2, days.length);
        assertEquals(Day.DAY1, days[0]);
        assertEquals(Day.DAY2, days[1]);
    }
}