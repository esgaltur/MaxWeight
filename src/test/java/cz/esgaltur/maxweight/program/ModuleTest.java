package cz.esgaltur.maxweight.program;

import cz.esgaltur.maxweight.core.event.TrainingProgramCreatedEvent;
import cz.esgaltur.maxweight.core.model.Week;
import cz.esgaltur.maxweight.program.service.ProgramFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Module test for the program module.
 * Verifies that events are published correctly.
 */
@SpringBootTest
@RecordApplicationEvents
public class ModuleTest {

    @Autowired
    private ProgramFactory programFactory;

    @Autowired
    private ApplicationEvents applicationEvents;

    /**
     * Test that a TrainingProgramCreatedEvent is published when a program is created.
     */
    @Test
    void shouldPublishEventWhenProgramIsCreated() {
        // Arrange
        Week week = Week.WEEK1;
        int maxWeight = 100;

        // Act
        programFactory.createProgram(week, maxWeight);

        // Assert
        assertThat(applicationEvents.stream(TrainingProgramCreatedEvent.class).count()).isEqualTo(1);

        TrainingProgramCreatedEvent event = applicationEvents.stream(TrainingProgramCreatedEvent.class)
            .findFirst()
            .orElseThrow();

        assertThat(event.getProgram().getWeek()).isEqualTo(week);
        assertThat(event.getProgram().getMaxWeight()).isEqualTo(maxWeight);
    }
}
