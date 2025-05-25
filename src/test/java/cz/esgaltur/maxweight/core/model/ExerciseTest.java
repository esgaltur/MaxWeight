package cz.esgaltur.maxweight.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Exercise class.
 */
public class ExerciseTest {

    /**
     * Test the constructor and getters.
     */
    @Test
    public void testConstructorAndGetters() {
        // Create an exercise
        int weightPercentage = 75;
        double actualWeight = 75.0;
        int repetitions = 5;
        Day day = Day.DAY1;
        
        Exercise exercise = new Exercise(weightPercentage, actualWeight, repetitions, day);
        
        // Test getters
        assertEquals(weightPercentage, exercise.getWeightPercentage());
        assertEquals(actualWeight, exercise.getActualWeight());
        assertEquals(repetitions, exercise.getRepetitions());
        assertEquals(day, exercise.getDay());
    }

    /**
     * Test the create factory method.
     */
    @Test
    public void testCreate() {
        // Create an exercise using the factory method
        int percentage = 75;
        int maxWeight = 100;
        int repetitions = 5;
        Day day = Day.DAY1;
        
        Exercise exercise = Exercise.create(percentage, maxWeight, repetitions, day);
        
        // Test the created exercise
        assertEquals(percentage, exercise.getWeightPercentage());
        assertEquals(75.0, exercise.getActualWeight()); // 75% of 100 = 75.0
        assertEquals(repetitions, exercise.getRepetitions());
        assertEquals(day, exercise.getDay());
    }

    /**
     * Test the calculation of actual weight.
     */
    @Test
    public void testCalculateWeight() {
        // Test with various percentages and max weights
        Exercise exercise1 = Exercise.create(50, 100, 10, Day.DAY1);
        assertEquals(50.0, exercise1.getActualWeight()); // 50% of 100 = 50.0
        
        Exercise exercise2 = Exercise.create(75, 100, 5, Day.DAY1);
        assertEquals(75.0, exercise2.getActualWeight()); // 75% of 100 = 75.0
        
        Exercise exercise3 = Exercise.create(90, 100, 3, Day.DAY1);
        assertEquals(90.0, exercise3.getActualWeight()); // 90% of 100 = 90.0
        
        Exercise exercise4 = Exercise.create(75, 200, 5, Day.DAY1);
        assertEquals(150.0, exercise4.getActualWeight()); // 75% of 200 = 150.0
        
        Exercise exercise5 = Exercise.create(75, 50, 5, Day.DAY1);
        assertEquals(37.5, exercise5.getActualWeight()); // 75% of 50 = 37.5
    }

    /**
     * Test edge cases for weight calculation.
     */
    @Test
    public void testCalculateWeightEdgeCases() {
        // Test with 0% (should be 0.0)
        Exercise exercise1 = Exercise.create(0, 100, 10, Day.DAY1);
        assertEquals(0.0, exercise1.getActualWeight());
        
        // Test with 100% (should be the max weight)
        Exercise exercise2 = Exercise.create(100, 100, 1, Day.DAY1);
        assertEquals(100.0, exercise2.getActualWeight());
        
        // Test with 0 max weight (should be 0.0 regardless of percentage)
        Exercise exercise3 = Exercise.create(75, 0, 5, Day.DAY1);
        assertEquals(0.0, exercise3.getActualWeight());
    }
}