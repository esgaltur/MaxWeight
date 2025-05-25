package cz.esgaltur.maxweight.program.service;

import cz.esgaltur.maxweight.core.event.TrainingProgramCreatedEvent;
import cz.esgaltur.maxweight.core.model.Day;
import cz.esgaltur.maxweight.core.model.Exercise;
import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.core.model.Week;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ProgramFactory class.
 */
public class ProgramFactoryTest {

    @Mock
    private ProgramDataService dataService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private ProgramFactory programFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        programFactory = new ProgramFactory(dataService, eventPublisher);

        // Set up mock data
        when(dataService.getWeightPercentages(Week.WEEK1, Day.DAY1)).thenReturn(new int[]{45, 55, 65, 70, 70, 70});
        when(dataService.getRepetitionCounts(Week.WEEK1, Day.DAY1)).thenReturn(new int[]{10, 8, 6, 6, 6, 6});

        when(dataService.getWeightPercentages(Week.WEEK1, Day.DAY2)).thenReturn(new int[]{45, 55, 65, 75, 80});
        when(dataService.getRepetitionCounts(Week.WEEK1, Day.DAY2)).thenReturn(new int[]{10, 8, 5, 5, 5});
    }

    /**
     * Test the createProgram method.
     */
    @Test
    public void testCreateProgram() {
        // Create a program
        Week week = Week.WEEK1;
        int maxWeight = 100;

        TrainingProgram program = programFactory.createProgram(week, maxWeight);

        // Verify the program properties
        assertNotNull(program);
        assertEquals(week, program.getWeek());
        assertEquals(maxWeight, program.getMaxWeight());

        // Verify the exercises for Day 1
        assertNotNull(program.getExercisesForDay(Day.DAY1));
        assertEquals(6, program.getExercisesForDay(Day.DAY1).size());

        // Check the first exercise for Day 1
        Exercise firstExerciseDay1 = program.getExercisesForDay(Day.DAY1).get(0);
        assertEquals(45, firstExerciseDay1.getWeightPercentage());
        assertEquals(45.0, firstExerciseDay1.getActualWeight());
        assertEquals(10, firstExerciseDay1.getRepetitions());
        assertEquals(Day.DAY1, firstExerciseDay1.getDay());

        // Check the last exercise for Day 1
        Exercise lastExerciseDay1 = program.getExercisesForDay(Day.DAY1).get(5);
        assertEquals(70, lastExerciseDay1.getWeightPercentage());
        assertEquals(70.0, lastExerciseDay1.getActualWeight());
        assertEquals(6, lastExerciseDay1.getRepetitions());
        assertEquals(Day.DAY1, lastExerciseDay1.getDay());

        // Verify the exercises for Day 2
        assertNotNull(program.getExercisesForDay(Day.DAY2));
        assertEquals(5, program.getExercisesForDay(Day.DAY2).size());

        // Check the first exercise for Day 2
        Exercise firstExerciseDay2 = program.getExercisesForDay(Day.DAY2).get(0);
        assertEquals(45, firstExerciseDay2.getWeightPercentage());
        assertEquals(45.0, firstExerciseDay2.getActualWeight());
        assertEquals(10, firstExerciseDay2.getRepetitions());
        assertEquals(Day.DAY2, firstExerciseDay2.getDay());

        // Verify that the data service methods were called
        verify(dataService).getWeightPercentages(week, Day.DAY1);
        verify(dataService).getRepetitionCounts(week, Day.DAY1);
        verify(dataService).getWeightPercentages(week, Day.DAY2);
        verify(dataService).getRepetitionCounts(week, Day.DAY2);

        // Verify that the event publisher was called with the correct event
        ArgumentCaptor<TrainingProgramCreatedEvent> eventCaptor = ArgumentCaptor.forClass(TrainingProgramCreatedEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        TrainingProgramCreatedEvent event = eventCaptor.getValue();
        assertEquals(program, event.getProgram());
    }

    /**
     * Test the createProgram method with different weeks.
     */
    @Test
    public void testCreateProgramWithDifferentWeeks() {
        // Test with Week 1
        testCreateProgramForWeek(Week.WEEK1, 100);

        // Test with Week 6
        when(dataService.getWeightPercentages(Week.WEEK6, Day.DAY1)).thenReturn(new int[]{45, 55, 65, 75, 85});
        when(dataService.getRepetitionCounts(Week.WEEK6, Day.DAY1)).thenReturn(new int[]{10, 8, 5, 3, 2});
        when(dataService.getWeightPercentages(Week.WEEK6, Day.DAY2)).thenReturn(new int[]{45, 55, 65, 75, 85});
        when(dataService.getRepetitionCounts(Week.WEEK6, Day.DAY2)).thenReturn(new int[]{10, 8, 5, 3, 2});

        testCreateProgramForWeek(Week.WEEK6, 100);
    }

    private void testCreateProgramForWeek(Week week, int maxWeight) {
        // Reset the mock to clear previous interactions
        reset(eventPublisher);

        TrainingProgram program = programFactory.createProgram(week, maxWeight);

        assertNotNull(program);
        assertEquals(week, program.getWeek());
        assertEquals(maxWeight, program.getMaxWeight());

        // Verify that the data service methods were called
        verify(dataService, atLeastOnce()).getWeightPercentages(week, Day.DAY1);
        verify(dataService, atLeastOnce()).getRepetitionCounts(week, Day.DAY1);
        verify(dataService, atLeastOnce()).getWeightPercentages(week, Day.DAY2);
        verify(dataService, atLeastOnce()).getRepetitionCounts(week, Day.DAY2);

        // Verify that the event publisher was called with the correct event
        ArgumentCaptor<TrainingProgramCreatedEvent> eventCaptor = ArgumentCaptor.forClass(TrainingProgramCreatedEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        TrainingProgramCreatedEvent event = eventCaptor.getValue();
        assertEquals(program, event.getProgram());
    }
}
