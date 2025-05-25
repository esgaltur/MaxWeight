package cz.esgaltur.maxweight.model;

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
        // Arrange
        int weightPercentage = 70;
        double actualWeight = 70.0;
        int repetitions = 5;
        Day day = Day.DAY1;

        // Act
        Exercise exercise = new Exercise(weightPercentage, actualWeight, repetitions, day);

        // Assert
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
        // Arrange
        int percentage = 75;
        int maxWeight = 100;
        int repetitions = 5;
        Day day = Day.DAY2;

        // Act
        Exercise exercise = Exercise.create(percentage, maxWeight, repetitions, day);

        // Assert
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
        
        // Test case 1: 50% of 100kg
        Exercise exercise1 = Exercise.create(50, 100, 10, Day.DAY1);
        assertEquals(50.0, exercise1.getActualWeight());
        
        // Test case 2: 75% of 100kg
        Exercise exercise2 = Exercise.create(75, 100, 5, Day.DAY1);
        assertEquals(75.0, exercise2.getActualWeight());
        
        // Test case 3: 90% of 100kg
        Exercise exercise3 = Exercise.create(90, 100, 3, Day.DAY1);
        assertEquals(90.0, exercise3.getActualWeight());
        
        // Test case 4: 45% of 200kg
        Exercise exercise4 = Exercise.create(45, 200, 10, Day.DAY1);
        assertEquals(90.0, exercise4.getActualWeight());
        
        // Test case 5: 85% of 150kg
        Exercise exercise5 = Exercise.create(85, 150, 3, Day.DAY1);
        assertEquals(127.5, exercise5.getActualWeight());
    }

    /**
     * Test edge cases for the calculation of actual weight.
     */
    @Test
    public void testCalculateWeightEdgeCases() {
        // Test with edge cases
        
        // Test case 1: 0% of any weight should be 0
        Exercise exercise1 = Exercise.create(0, 100, 10, Day.DAY1);
        assertEquals(0.0, exercise1.getActualWeight());
        
        // Test case 2: Any percentage of 0 weight should be 0
        Exercise exercise2 = Exercise.create(75, 0, 5, Day.DAY1);
        assertEquals(0.0, exercise2.getActualWeight());
        
        // Test case 3: 100% of weight should be the weight itself
        Exercise exercise3 = Exercise.create(100, 150, 1, Day.DAY1);
        assertEquals(150.0, exercise3.getActualWeight());
    }
}