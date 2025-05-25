package cz.esgaltur.maxweight.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Week enum.
 */
public class WeekTest {

    /**
     * Test the getWeekNumber method.
     */
    @Test
    public void testGetWeekNumber() {
        assertEquals(1, Week.WEEK1.getWeekNumber());
        assertEquals(2, Week.WEEK2.getWeekNumber());
        assertEquals(3, Week.WEEK3.getWeekNumber());
        assertEquals(4, Week.WEEK4.getWeekNumber());
        assertEquals(5, Week.WEEK5.getWeekNumber());
        assertEquals(6, Week.WEEK6.getWeekNumber());
    }

    /**
     * Test the fromWeekNumber method with valid inputs.
     */
    @Test
    public void testFromWeekNumberValid() {
        assertEquals(Week.WEEK1, Week.fromWeekNumber(1));
        assertEquals(Week.WEEK2, Week.fromWeekNumber(2));
        assertEquals(Week.WEEK3, Week.fromWeekNumber(3));
        assertEquals(Week.WEEK4, Week.fromWeekNumber(4));
        assertEquals(Week.WEEK5, Week.fromWeekNumber(5));
        assertEquals(Week.WEEK6, Week.fromWeekNumber(6));
    }

    /**
     * Test the fromWeekNumber method with invalid inputs.
     */
    @Test
    public void testFromWeekNumberInvalid() {
        // Test with week number less than 1
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Week.fromWeekNumber(0));
        assertTrue(exception.getMessage().contains("Invalid week number"));

        // Test with week number greater than 6
        exception = assertThrows(IllegalArgumentException.class, () -> Week.fromWeekNumber(7));
        assertTrue(exception.getMessage().contains("Invalid week number"));
    }

    /**
     * Test the toLegacyKey method.
     */
    @Test
    public void testToLegacyKey() {
        assertEquals("Week1", Week.WEEK1.toLegacyKey());
        assertEquals("Week2", Week.WEEK2.toLegacyKey());
        assertEquals("Week3", Week.WEEK3.toLegacyKey());
        assertEquals("Week4", Week.WEEK4.toLegacyKey());
        assertEquals("Week5", Week.WEEK5.toLegacyKey());
        assertEquals("Week6", Week.WEEK6.toLegacyKey());
    }

    /**
     * Test the toLegacyCountKey method.
     */
    @Test
    public void testToLegacyCountKey() {
        assertEquals("CountW1", Week.WEEK1.toLegacyCountKey());
        assertEquals("CountW2", Week.WEEK2.toLegacyCountKey());
        assertEquals("CountW3", Week.WEEK3.toLegacyCountKey());
        assertEquals("CountW4", Week.WEEK4.toLegacyCountKey());
        assertEquals("CountW5", Week.WEEK5.toLegacyCountKey());
        assertEquals("CountW6", Week.WEEK6.toLegacyCountKey());
    }
}