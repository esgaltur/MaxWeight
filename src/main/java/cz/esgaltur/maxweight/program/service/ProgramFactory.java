package cz.esgaltur.maxweight.program.service;

import cz.esgaltur.maxweight.core.event.TrainingProgramCreatedEvent;
import cz.esgaltur.maxweight.core.model.Day;
import cz.esgaltur.maxweight.core.model.Exercise;
import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.core.model.Week;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Factory class for creating training programs.
 * Uses the ProgramDataService to get the data and creates a TrainingProgram with the appropriate exercises.
 * Publishes a TrainingProgramCreatedEvent when a program is created.
 */
@Service
public class ProgramFactory {
    private final ProgramDataService dataService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor
     * 
     * @param dataService The data service to use
     * @param eventPublisher The event publisher to use
     */
    @Autowired
    public ProgramFactory(ProgramDataService dataService, ApplicationEventPublisher eventPublisher) {
        this.dataService = dataService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Create a training program for a specific week
     * 
     * @param week The week to create a program for
     * @param maxWeight The maximum weight for bench press
     * @return A new TrainingProgram instance
     */
    public TrainingProgram createProgram(Week week, int maxWeight) {
        TrainingProgram program = new TrainingProgram(week, maxWeight);

        // Add exercises for day 1
        addExercisesForDay(program, Day.DAY1);

        // Add exercises for day 2
        addExercisesForDay(program, Day.DAY2);

        // Publish event that a new program has been created
        eventPublisher.publishEvent(new TrainingProgramCreatedEvent(program));

        return program;
    }

    /**
     * Add exercises for a specific day to a training program
     * 
     * @param program The training program to add exercises to
     * @param day The day to add exercises for
     */
    private void addExercisesForDay(TrainingProgram program, Day day) {
        Week week = program.getWeek();
        int maxWeight = program.getMaxWeight();

        int[] percentages = dataService.getWeightPercentages(week, day);
        int[] repetitions = dataService.getRepetitionCounts(week, day);

        for (int i = 0; i < percentages.length; i++) {
            Exercise exercise = Exercise.create(
                percentages[i],
                maxWeight,
                repetitions[i],
                day
            );
            program.addExercise(exercise);
        }
    }
}
