package cz.esgaltur.maxweight.core.event;

import cz.esgaltur.maxweight.core.model.TrainingProgram;

/**
 * Domain event that is published when a new training program is created.
 * This event can be consumed by other modules that need to react to program creation.
 */
public class TrainingProgramCreatedEvent {
    
    private final TrainingProgram program;
    
    /**
     * Constructor
     * 
     * @param program The training program that was created
     */
    public TrainingProgramCreatedEvent(TrainingProgram program) {
        this.program = program;
    }
    
    /**
     * Get the training program that was created
     * 
     * @return The training program
     */
    public TrainingProgram getProgram() {
        return program;
    }
}