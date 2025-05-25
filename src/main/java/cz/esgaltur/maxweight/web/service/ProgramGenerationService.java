package cz.esgaltur.maxweight.web.service;

import cz.esgaltur.maxweight.core.event.ProgramsGeneratedEvent;
import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.core.model.Week;
import cz.esgaltur.maxweight.program.service.ProgramFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for generating training programs.
 * Extracts common program generation logic used across controllers.
 */
@Service
public class ProgramGenerationService {

    private final ProgramFactory programFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ProgramGenerationService(ProgramFactory programFactory, ApplicationEventPublisher eventPublisher) {
        this.programFactory = programFactory;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Generate a single training program for a specific week
     *
     * @param weekNumber The week number (1-6)
     * @param maxWeight The maximum weight for bench press
     * @return The generated training program
     */
    public TrainingProgram generateProgram(int weekNumber, int maxWeight) {
        Week week = Week.fromWeekNumber(weekNumber);
        return programFactory.createProgram(week, maxWeight);
    }

    /**
     * Generate multiple training programs for a range of weeks
     *
     * @param fromWeek The starting week number
     * @param toWeek The ending week number
     * @param maxWeight The maximum weight for bench press
     * @return The list of generated training programs
     */
    public List<TrainingProgram> generatePrograms(int fromWeek, int toWeek, int maxWeight) {
        List<TrainingProgram> programs = new ArrayList<>();
        for (int weekNumber = fromWeek; weekNumber <= toWeek; weekNumber++) {
            programs.add(generateProgram(weekNumber, maxWeight));
        }
        
        // Publish event that programs were generated
        publishProgramsGeneratedEvent(programs, maxWeight, fromWeek, toWeek);
        
        return programs;
    }
    
    /**
     * Publish an event indicating that programs were generated
     *
     * @param programs The generated programs
     * @param maxWeight The maximum weight used
     * @param fromWeek The starting week number
     * @param toWeek The ending week number
     */
    public void publishProgramsGeneratedEvent(List<TrainingProgram> programs, int maxWeight, int fromWeek, int toWeek) {
        eventPublisher.publishEvent(new ProgramsGeneratedEvent(programs, maxWeight, fromWeek, toWeek));
    }
}