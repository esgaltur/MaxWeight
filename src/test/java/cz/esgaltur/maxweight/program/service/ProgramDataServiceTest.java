package cz.esgaltur.maxweight.program.service;

import cz.esgaltur.maxweight.core.model.Day;
import cz.esgaltur.maxweight.core.model.Week;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ProgramDataService class.
 */
public class ProgramDataServiceTest {
    
    private ProgramDataService dataService;
    
    @BeforeEach
    public void setUp() {
        dataService = new ProgramDataService();
    }

    /**
     * Test the getWeightPercentages method.
     */
    @Test
    public void testGetWeightPercentages() {
        // Test for Week 1, Day 1
        int[] week1Day1Percentages = dataService.getWeightPercentages(Week.WEEK1, Day.DAY1);
        assertNotNull(week1Day1Percentages);
        assertEquals(6, week1Day1Percentages.length);
        assertEquals(45, week1Day1Percentages[0]);
        assertEquals(55, week1Day1Percentages[1]);
        assertEquals(65, week1Day1Percentages[2]);
        assertEquals(70, week1Day1Percentages[3]);
        assertEquals(70, week1Day1Percentages[4]);
        assertEquals(70, week1Day1Percentages[5]);
        
        // Test for Week 1, Day 2
        int[] week1Day2Percentages = dataService.getWeightPercentages(Week.WEEK1, Day.DAY2);
        assertNotNull(week1Day2Percentages);
        assertEquals(10, week1Day2Percentages.length);
        assertEquals(45, week1Day2Percentages[0]);
        assertEquals(55, week1Day2Percentages[1]);
        assertEquals(65, week1Day2Percentages[2]);
        assertEquals(75, week1Day2Percentages[3]);
        assertEquals(80, week1Day2Percentages[4]);
        assertEquals(80, week1Day2Percentages[5]);
        assertEquals(80, week1Day2Percentages[6]);
        assertEquals(75, week1Day2Percentages[7]);
        assertEquals(65, week1Day2Percentages[8]);
        assertEquals(55, week1Day2Percentages[9]);
        
        // Test for Week 6, Day 1
        int[] week6Day1Percentages = dataService.getWeightPercentages(Week.WEEK6, Day.DAY1);
        assertNotNull(week6Day1Percentages);
        assertEquals(5, week6Day1Percentages.length);
        assertEquals(45, week6Day1Percentages[0]);
        assertEquals(55, week6Day1Percentages[1]);
        assertEquals(65, week6Day1Percentages[2]);
        assertEquals(75, week6Day1Percentages[3]);
        assertEquals(85, week6Day1Percentages[4]);
    }

    /**
     * Test the getRepetitionCounts method.
     */
    @Test
    public void testGetRepetitionCounts() {
        // Test for Week 1, Day 1
        int[] week1Day1Counts = dataService.getRepetitionCounts(Week.WEEK1, Day.DAY1);
        assertNotNull(week1Day1Counts);
        assertEquals(6, week1Day1Counts.length);
        assertEquals(10, week1Day1Counts[0]);
        assertEquals(8, week1Day1Counts[1]);
        assertEquals(6, week1Day1Counts[2]);
        assertEquals(6, week1Day1Counts[3]);
        assertEquals(6, week1Day1Counts[4]);
        assertEquals(6, week1Day1Counts[5]);
        
        // Test for Week 1, Day 2
        int[] week1Day2Counts = dataService.getRepetitionCounts(Week.WEEK1, Day.DAY2);
        assertNotNull(week1Day2Counts);
        assertEquals(10, week1Day2Counts.length);
        assertEquals(10, week1Day2Counts[0]);
        assertEquals(8, week1Day2Counts[1]);
        assertEquals(5, week1Day2Counts[2]);
        assertEquals(5, week1Day2Counts[3]);
        assertEquals(5, week1Day2Counts[4]);
        assertEquals(5, week1Day2Counts[5]);
        assertEquals(5, week1Day2Counts[6]);
        assertEquals(5, week1Day2Counts[7]);
        assertEquals(8, week1Day2Counts[8]);
        assertEquals(12, week1Day2Counts[9]);
        
        // Test for Week 6, Day 1
        int[] week6Day1Counts = dataService.getRepetitionCounts(Week.WEEK6, Day.DAY1);
        assertNotNull(week6Day1Counts);
        assertEquals(5, week6Day1Counts.length);
        assertEquals(10, week6Day1Counts[0]);
        assertEquals(8, week6Day1Counts[1]);
        assertEquals(5, week6Day1Counts[2]);
        assertEquals(3, week6Day1Counts[3]);
        assertEquals(2, week6Day1Counts[4]);
    }

    /**
     * Test the getExerciseCount method.
     */
    @Test
    public void testGetExerciseCount() {
        // Test for Week 1
        assertEquals(6, dataService.getExerciseCount(Week.WEEK1, Day.DAY1));
        assertEquals(10, dataService.getExerciseCount(Week.WEEK1, Day.DAY2));
        
        // Test for Week 2
        assertEquals(6, dataService.getExerciseCount(Week.WEEK2, Day.DAY1));
        assertEquals(10, dataService.getExerciseCount(Week.WEEK2, Day.DAY2));
        
        // Test for Week 6
        assertEquals(5, dataService.getExerciseCount(Week.WEEK6, Day.DAY1));
        assertEquals(5, dataService.getExerciseCount(Week.WEEK6, Day.DAY2));
    }

    /**
     * Test the getMinExerciseCount method.
     */
    @Test
    public void testGetMinExerciseCount() {
        // Test for all weeks
        assertEquals(6, dataService.getMinExerciseCount(Week.WEEK1));
        assertEquals(6, dataService.getMinExerciseCount(Week.WEEK2));
        assertEquals(6, dataService.getMinExerciseCount(Week.WEEK3));
        assertEquals(6, dataService.getMinExerciseCount(Week.WEEK4));
        assertEquals(5, dataService.getMinExerciseCount(Week.WEEK5));
        assertEquals(5, dataService.getMinExerciseCount(Week.WEEK6));
    }

    /**
     * Test that the arrays for weight percentages and repetition counts have the same length.
     */
    @Test
    public void testArrayLengthsMatch() {
        for (Week week : Week.values()) {
            for (Day day : Day.values()) {
                int[] percentages = dataService.getWeightPercentages(week, day);
                int[] counts = dataService.getRepetitionCounts(week, day);
                assertEquals(percentages.length, counts.length, 
                    "Array lengths should match for " + week + ", " + day);
            }
        }
    }
}