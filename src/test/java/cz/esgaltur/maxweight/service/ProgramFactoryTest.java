package cz.esgaltur.maxweight.service;

import cz.esgaltur.maxweight.model.Day;
import cz.esgaltur.maxweight.model.Exercise;
import cz.esgaltur.maxweight.model.TrainingProgram;
import cz.esgaltur.maxweight.model.Week;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ProgramFactory class.
 */
public class ProgramFactoryTest {

    @Mock
    private ProgramDataService mockDataService;

    private ProgramFactory programFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        programFactory = new ProgramFactory(mockDataService);
    }

    /**
     * Test the createProgram method.
     */
    @Test
    public void testCreateProgram() {
        // Arrange
        Week week = Week.WEEK3;
        int maxWeight = 100;
        
        // Mock the data service responses
        int[] day1Percentages = {45, 55, 70, 75, 80, 85};
        int[] day1Repetitions = {10, 8, 4, 3, 3, 3};
        int[] day2Percentages = {45, 55, 65, 75, 85, 90, 90, 80, 60};
        int[] day2Repetitions = {10, 8, 5, 4, 3, 3, 3, 5, 10};
        
        when(mockDataService.getWeightPercentages(week, Day.DAY1)).thenReturn(day1Percentages);
        when(mockDataService.getRepetitionCounts(week, Day.DAY1)).thenReturn(day1Repetitions);
        when(mockDataService.getWeightPercentages(week, Day.DAY2)).thenReturn(day2Percentages);
        when(mockDataService.getRepetitionCounts(week, Day.DAY2)).thenReturn(day2Repetitions);
        
        // Act
        TrainingProgram program = programFactory.createProgram(week, maxWeight);
        
        // Assert
        assertNotNull(program);
        assertEquals(week, program.getWeek());
        assertEquals(maxWeight, program.getMaxWeight());
        
        // Verify day 1 exercises
        List<Exercise> day1Exercises = program.getExercisesForDay(Day.DAY1);
        assertEquals(day1Percentages.length, day1Exercises.size());
        for (int i = 0; i < day1Percentages.length; i++) {
            Exercise exercise = day1Exercises.get(i);
            assertEquals(day1Percentages[i], exercise.getWeightPercentage());
            assertEquals(day1Repetitions[i], exercise.getRepetitions());
            assertEquals(Day.DAY1, exercise.getDay());
            assertEquals((day1Percentages[i] * maxWeight) / 100.0, exercise.getActualWeight());
        }
        
        // Verify day 2 exercises
        List<Exercise> day2Exercises = program.getExercisesForDay(Day.DAY2);
        assertEquals(day2Percentages.length, day2Exercises.size());
        for (int i = 0; i < day2Percentages.length; i++) {
            Exercise exercise = day2Exercises.get(i);
            assertEquals(day2Percentages[i], exercise.getWeightPercentage());
            assertEquals(day2Repetitions[i], exercise.getRepetitions());
            assertEquals(Day.DAY2, exercise.getDay());
            assertEquals((day2Percentages[i] * maxWeight) / 100.0, exercise.getActualWeight());
        }
        
        // Verify that the data service methods were called
        verify(mockDataService).getWeightPercentages(week, Day.DAY1);
        verify(mockDataService).getRepetitionCounts(week, Day.DAY1);
        verify(mockDataService).getWeightPercentages(week, Day.DAY2);
        verify(mockDataService).getRepetitionCounts(week, Day.DAY2);
    }

    /**
     * Test the createProgram method with different weeks.
     */
    @Test
    public void testCreateProgramWithDifferentWeeks() {
        // Test with Week 1
        testCreateProgramForWeek(Week.WEEK1, 100);
        
        // Test with Week 6
        testCreateProgramForWeek(Week.WEEK6, 120);
    }

    /**
     * Helper method to test createProgram for a specific week.
     */
    private void testCreateProgramForWeek(Week week, int maxWeight) {
        // Arrange
        int[] day1Percentages = {45, 55, 65};
        int[] day1Repetitions = {10, 8, 6};
        int[] day2Percentages = {45, 55, 65, 75};
        int[] day2Repetitions = {10, 8, 5, 5};
        
        when(mockDataService.getWeightPercentages(week, Day.DAY1)).thenReturn(day1Percentages);
        when(mockDataService.getRepetitionCounts(week, Day.DAY1)).thenReturn(day1Repetitions);
        when(mockDataService.getWeightPercentages(week, Day.DAY2)).thenReturn(day2Percentages);
        when(mockDataService.getRepetitionCounts(week, Day.DAY2)).thenReturn(day2Repetitions);
        
        // Act
        TrainingProgram program = programFactory.createProgram(week, maxWeight);
        
        // Assert
        assertNotNull(program);
        assertEquals(week, program.getWeek());
        assertEquals(maxWeight, program.getMaxWeight());
        
        // Verify day 1 exercises
        List<Exercise> day1Exercises = program.getExercisesForDay(Day.DAY1);
        assertEquals(day1Percentages.length, day1Exercises.size());
        
        // Verify day 2 exercises
        List<Exercise> day2Exercises = program.getExercisesForDay(Day.DAY2);
        assertEquals(day2Percentages.length, day2Exercises.size());
        
        // Verify that the data service methods were called
        verify(mockDataService).getWeightPercentages(week, Day.DAY1);
        verify(mockDataService).getRepetitionCounts(week, Day.DAY1);
        verify(mockDataService).getWeightPercentages(week, Day.DAY2);
        verify(mockDataService).getRepetitionCounts(week, Day.DAY2);
    }
}