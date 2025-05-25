package cz.esgaltur.maxweight.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TrainingProgram class.
 */
 class TrainingProgramTest {

    /**
     * Test the constructor and getters.
     */
    @Test
     void testConstructorAndGetters() {
        // Create a training program
        Week week = Week.WEEK1;
        int maxWeight = 100;

        TrainingProgram program = new TrainingProgram(week, maxWeight);

        // Test getters
        assertEquals(week, program.getWeek());
        assertEquals(maxWeight, program.getMaxWeight());
    }

    /**
     * Test that the constructor initializes empty lists for each day.
     */
    @Test
     void testConstructorInitializesEmptyLists() {
        // Create a training program
        Week week = Week.WEEK1;
        int maxWeight = 100;

        TrainingProgram program = new TrainingProgram(week, maxWeight);

        // Test that the lists are initialized and empty
        assertNotNull(program.getExercisesForDay(Day.DAY1));
        assertNotNull(program.getExercisesForDay(Day.DAY2));

        assertTrue(program.getExercisesForDay(Day.DAY1).isEmpty());
        assertTrue(program.getExercisesForDay(Day.DAY2).isEmpty());
    }

    /**
     * Test adding exercises to the program.
     */
    @Test
     void testAddExercise() {
        // Create a training program
        Week week = Week.WEEK1;
        int maxWeight = 100;

        TrainingProgram program = new TrainingProgram(week, maxWeight);

        // Create an exercise
        Exercise exercise = Exercise.create(75, maxWeight, 5, Day.DAY1);

        // Add the exercise to the program
        program.addExercise(exercise);

        // Test that the exercise was added to the correct day
        assertEquals(1, program.getExercisesForDay(Day.DAY1).size());
        assertEquals(0, program.getExercisesForDay(Day.DAY2).size());

        // Test that the exercise in the list is the one we added
        Exercise addedExercise = program.getExercisesForDay(Day.DAY1).get(0);
        assertEquals(exercise.getWeightPercentage(), addedExercise.getWeightPercentage());
        assertEquals(exercise.getActualWeight(), addedExercise.getActualWeight());
        assertEquals(exercise.getRepetitions(), addedExercise.getRepetitions());
        assertEquals(exercise.getDay(), addedExercise.getDay());
    }

    /**
     * Test adding multiple exercises to the same day.
     */
    @Test
     void testAddMultipleExercisesToSameDay() {
        // Create a training program
        Week week = Week.WEEK1;
        int maxWeight = 100;

        TrainingProgram program = new TrainingProgram(week, maxWeight);

        // Create exercises
        Exercise exercise1 = Exercise.create(75, maxWeight, 5, Day.DAY1);
        Exercise exercise2 = Exercise.create(85, maxWeight, 3, Day.DAY1);

        // Add the exercises to the program
        program.addExercise(exercise1);
        program.addExercise(exercise2);

        // Test that both exercises were added to the correct day
        assertEquals(2, program.getExercisesForDay(Day.DAY1).size());
        assertEquals(0, program.getExercisesForDay(Day.DAY2).size());

        // Test that the exercises in the list are the ones we added
        Exercise addedExercise1 = program.getExercisesForDay(Day.DAY1).get(0);
        Exercise addedExercise2 = program.getExercisesForDay(Day.DAY1).get(1);

        assertEquals(exercise1.getWeightPercentage(), addedExercise1.getWeightPercentage());
        assertEquals(exercise2.getWeightPercentage(), addedExercise2.getWeightPercentage());
    }

    /**
     * Test the isSpecialWeek method.
     */
    @Test
     void testIsSpecialWeek() {
        // Test with non-special weeks (1-4)
        assertFalse(new TrainingProgram(Week.WEEK1, 100).isSpecialWeek());
        assertFalse(new TrainingProgram(Week.WEEK2, 100).isSpecialWeek());
        assertFalse(new TrainingProgram(Week.WEEK3, 100).isSpecialWeek());
        assertFalse(new TrainingProgram(Week.WEEK4, 100).isSpecialWeek());

        // Test with special weeks (5-6)
        assertTrue(new TrainingProgram(Week.WEEK5, 100).isSpecialWeek());
        assertTrue(new TrainingProgram(Week.WEEK6, 100).isSpecialWeek());
    }
}