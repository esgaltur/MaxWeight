package cz.esgaltur.maxweight.core.event;

import cz.esgaltur.maxweight.core.model.TrainingProgram;

import java.util.List;

/**
 * Domain event that is published when a user generates multiple training programs.
 * This event can be consumed by other modules that need to react to program generation.
 */
public class ProgramsGeneratedEvent {
    
    private final List<TrainingProgram> programs;
    private final int maxWeight;
    private final int fromWeek;
    private final int toWeek;
    
    /**
     * Constructor
     * 
     * @param programs The list of training programs that were generated
     * @param maxWeight The maximum weight used for the programs
     * @param fromWeek The starting week number
     * @param toWeek The ending week number
     */
    public ProgramsGeneratedEvent(List<TrainingProgram> programs, int maxWeight, int fromWeek, int toWeek) {
        this.programs = programs;
        this.maxWeight = maxWeight;
        this.fromWeek = fromWeek;
        this.toWeek = toWeek;
    }
    
    /**
     * Get the list of training programs that were generated
     * 
     * @return The list of training programs
     */
    public List<TrainingProgram> getPrograms() {
        return programs;
    }
    
    /**
     * Get the maximum weight used for the programs
     * 
     * @return The maximum weight
     */
    public int getMaxWeight() {
        return maxWeight;
    }
    
    /**
     * Get the starting week number
     * 
     * @return The starting week number
     */
    public int getFromWeek() {
        return fromWeek;
    }
    
    /**
     * Get the ending week number
     * 
     * @return The ending week number
     */
    public int getToWeek() {
        return toWeek;
    }
}