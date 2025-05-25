package cz.esgaltur.maxweight.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TrainingProgram class.
 */
public class TrainingProgramTest {

    /**
     * Test the constructor and getters.
     */
    @Test
    public void testConstructorAndGetters() {
        // Arrange
        Week week = Week.WEEK3;
        int maxWeight = 120;

        // Act
        TrainingProgram program = new TrainingProgram(week, maxWeight);

        // Assert
        assertEquals(week, program.getWeek());
        assertEquals(maxWeight, program.getMaxWeight());
    }

    /**
     * Test that the constructor initializes empty lists for each day.
     */
    @Test
    public void testConstructorInitializesEmptyLists() {
        // Arrange
        Week week = Week.WEEK2;
        int maxWeight = 100;

        // Act
        TrainingProgram program = new TrainingProgram(week, maxWeight);

        // Assert
        for (Day day : Day.values()) {
            List<Exercise> exercises = program.getExercisesForDay(day);
            assertNotNull(exercises, "Exercise list for " + day + " should not be null");
            assertTrue(exercises.isEmpty(), "Exercise list for " + day + " should be empty");
        }
    }

    /**
     * Test adding exercises to the program.
     */
    @Test
    public void testAddExercise() {
        // Arrange
        TrainingProgram program = new TrainingProgram(Week.WEEK1, 100);
        Exercise day1Exercise = new Exercise(70, 70.0, 5, Day.DAY1);
        Exercise day2Exercise = new Exercise(80, 80.0, 4, Day.DAY2);

        // Act
        program.addExercise(day1Exercise);
        program.addExercise(day2Exercise);

        // Assert
        List<Exercise> day1Exercises = program.getExercisesForDay(Day.DAY1);
        List<Exercise> day2Exercises = program.getExercisesForDay(Day.DAY2);
        
        assertEquals(1, day1Exercises.size());
        assertEquals(1, day2Exercises.size());
        
        assertEquals(day1Exercise, day1Exercises.get(0));
        assertEquals(day2Exercise, day2Exercises.get(0));
    }

    /**
     * Test adding multiple exercises to the same day.
     */
    @Test
    public void testAddMultipleExercisesToSameDay() {
        // Arrange
        TrainingProgram program = new TrainingProgram(Week.WEEK1, 100);
        Exercise exercise1 = new Exercise(70, 70.0, 5, Day.DAY1);
        Exercise exercise2 = new Exercise(80, 80.0, 4, Day.DAY1);
        Exercise exercise3 = new Exercise(90, 90.0, 3, Day.DAY1);

        // Act
        program.addExercise(exercise1);
        program.addExercise(exercise2);
        program.addExercise(exercise3);

        // Assert
        List<Exercise> exercises = program.getExercisesForDay(Day.DAY1);
        
        assertEquals(3, exercises.size());
        assertEquals(exercise1, exercises.get(0));
        assertEquals(exercise2, exercises.get(1));
        assertEquals(exercise3, exercises.get(2));
    }

    /**
     * Test the isSpecialWeek method.
     */
    @Test
    public void testIsSpecialWeek() {
        // Test for each week
        assertFalse(new TrainingProgram(Week.WEEK1, 100).isSpecialWeek());
        assertFalse(new TrainingProgram(Week.WEEK2, 100).isSpecialWeek());
        assertFalse(new TrainingProgram(Week.WEEK3, 100).isSpecialWeek());
        assertFalse(new TrainingProgram(Week.WEEK4, 100).isSpecialWeek());
        assertTrue(new TrainingProgram(Week.WEEK5, 100).isSpecialWeek());
        assertTrue(new TrainingProgram(Week.WEEK6, 100).isSpecialWeek());
    }
}