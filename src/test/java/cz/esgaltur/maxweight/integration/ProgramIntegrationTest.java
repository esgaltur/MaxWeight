package cz.esgaltur.maxweight.integration;

import cz.esgaltur.maxweight.model.Day;
import cz.esgaltur.maxweight.model.Exercise;
import cz.esgaltur.maxweight.model.TrainingProgram;
import cz.esgaltur.maxweight.model.Week;
import cz.esgaltur.maxweight.service.ProgramDataService;
import cz.esgaltur.maxweight.service.ProgramFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the program generation flow.
 * Tests the integration between ProgramDataService, ProgramFactory, and the model classes.
 */
public class ProgramIntegrationTest {

    private ProgramDataService dataService;
    private ProgramFactory programFactory;

    @BeforeEach
    public void setUp() {
        dataService = new ProgramDataService();
        programFactory = new ProgramFactory(dataService);
    }

    /**
     * Test the full flow of creating a training program for Week 1.
     */
    @Test
    public void testCreateProgramForWeek1() {
        // Arrange
        Week week = Week.WEEK1;
        int maxWeight = 100;

        // Act
        TrainingProgram program = programFactory.createProgram(week, maxWeight);

        // Assert
        assertNotNull(program);
        assertEquals(week, program.getWeek());
        assertEquals(maxWeight, program.getMaxWeight());
        assertFalse(program.isSpecialWeek());

        // Verify Day 1 exercises
        List<Exercise> day1Exercises = program.getExercisesForDay(Day.DAY1);
        assertNotNull(day1Exercises);
        assertEquals(6, day1Exercises.size());

        // Check first and last exercise for Day 1
        Exercise firstDay1Exercise = day1Exercises.get(0);
        assertEquals(45, firstDay1Exercise.getWeightPercentage());
        assertEquals(45.0, firstDay1Exercise.getActualWeight());
        assertEquals(10, firstDay1Exercise.getRepetitions());
        assertEquals(Day.DAY1, firstDay1Exercise.getDay());

        Exercise lastDay1Exercise = day1Exercises.get(5);
        assertEquals(70, lastDay1Exercise.getWeightPercentage());
        assertEquals(70.0, lastDay1Exercise.getActualWeight());
        assertEquals(6, lastDay1Exercise.getRepetitions());
        assertEquals(Day.DAY1, lastDay1Exercise.getDay());

        // Verify Day 2 exercises
        List<Exercise> day2Exercises = program.getExercisesForDay(Day.DAY2);
        assertNotNull(day2Exercises);
        assertEquals(10, day2Exercises.size());

        // Check first and last exercise for Day 2
        Exercise firstDay2Exercise = day2Exercises.get(0);
        assertEquals(45, firstDay2Exercise.getWeightPercentage());
        assertEquals(45.0, firstDay2Exercise.getActualWeight());
        assertEquals(10, firstDay2Exercise.getRepetitions());
        assertEquals(Day.DAY2, firstDay2Exercise.getDay());

        Exercise lastDay2Exercise = day2Exercises.get(9);
        assertEquals(55, lastDay2Exercise.getWeightPercentage());
        assertEquals(55.0, lastDay2Exercise.getActualWeight());
        assertEquals(12, lastDay2Exercise.getRepetitions());
        assertEquals(Day.DAY2, lastDay2Exercise.getDay());
    }

    /**
     * Test the full flow of creating a training program for Week 6 (special week).
     */
    @Test
    public void testCreateProgramForWeek6() {
        // Arrange
        Week week = Week.WEEK6;
        int maxWeight = 150;

        // Act
        TrainingProgram program = programFactory.createProgram(week, maxWeight);

        // Assert
        assertNotNull(program);
        assertEquals(week, program.getWeek());
        assertEquals(maxWeight, program.getMaxWeight());
        assertTrue(program.isSpecialWeek());

        // Verify Day 1 exercises
        List<Exercise> day1Exercises = program.getExercisesForDay(Day.DAY1);
        assertNotNull(day1Exercises);
        assertEquals(5, day1Exercises.size());

        // Check first and last exercise for Day 1
        Exercise firstDay1Exercise = day1Exercises.get(0);
        assertEquals(45, firstDay1Exercise.getWeightPercentage());
        assertEquals(67.5, firstDay1Exercise.getActualWeight());
        assertEquals(10, firstDay1Exercise.getRepetitions());
        assertEquals(Day.DAY1, firstDay1Exercise.getDay());

        Exercise lastDay1Exercise = day1Exercises.get(4);
        assertEquals(85, lastDay1Exercise.getWeightPercentage());
        assertEquals(127.5, lastDay1Exercise.getActualWeight());
        assertEquals(2, lastDay1Exercise.getRepetitions());
        assertEquals(Day.DAY1, lastDay1Exercise.getDay());

        // Verify Day 2 exercises
        List<Exercise> day2Exercises = program.getExercisesForDay(Day.DAY2);
        assertNotNull(day2Exercises);
        assertEquals(5, day2Exercises.size());

        // Check first and last exercise for Day 2
        Exercise firstDay2Exercise = day2Exercises.get(0);
        assertEquals(45, firstDay2Exercise.getWeightPercentage());
        assertEquals(67.5, firstDay2Exercise.getActualWeight());
        assertEquals(10, firstDay2Exercise.getRepetitions());
        assertEquals(Day.DAY2, firstDay2Exercise.getDay());

        Exercise lastDay2Exercise = day2Exercises.get(4);
        assertEquals(85, lastDay2Exercise.getWeightPercentage());
        assertEquals(127.5, lastDay2Exercise.getActualWeight());
        assertEquals(2, lastDay2Exercise.getRepetitions());
        assertEquals(Day.DAY2, lastDay2Exercise.getDay());
    }

    /**
     * Test creating programs for all weeks with different max weights.
     */
    @Test
    public void testCreateProgramsForAllWeeks() {
        // Test for all weeks with different max weights
        testProgramForWeek(Week.WEEK1, 100);
        testProgramForWeek(Week.WEEK2, 110);
        testProgramForWeek(Week.WEEK3, 120);
        testProgramForWeek(Week.WEEK4, 130);
        testProgramForWeek(Week.WEEK5, 140);
        testProgramForWeek(Week.WEEK6, 150);
    }

    /**
     * Helper method to test program creation for a specific week.
     */
    private void testProgramForWeek(Week week, int maxWeight) {
        // Create program
        TrainingProgram program = programFactory.createProgram(week, maxWeight);

        // Basic assertions
        assertNotNull(program);
        assertEquals(week, program.getWeek());
        assertEquals(maxWeight, program.getMaxWeight());

        // Verify exercises for both days
        List<Exercise> day1Exercises = program.getExercisesForDay(Day.DAY1);
        List<Exercise> day2Exercises = program.getExercisesForDay(Day.DAY2);

        assertNotNull(day1Exercises);
        assertNotNull(day2Exercises);
        assertFalse(day1Exercises.isEmpty());
        assertFalse(day2Exercises.isEmpty());

        // Verify exercise counts match the data service
        assertEquals(dataService.getExerciseCount(week, Day.DAY1), day1Exercises.size());
        assertEquals(dataService.getExerciseCount(week, Day.DAY2), day2Exercises.size());

        // Verify all exercises have the correct day
        for (Exercise exercise : day1Exercises) {
            assertEquals(Day.DAY1, exercise.getDay());
        }

        for (Exercise exercise : day2Exercises) {
            assertEquals(Day.DAY2, exercise.getDay());
        }
    }
}