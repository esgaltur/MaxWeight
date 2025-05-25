package cz.esgaltur.maxweight.web.listener;

import brave.Tracer;
import cz.esgaltur.maxweight.core.event.ProgramsGeneratedEvent;
import cz.esgaltur.maxweight.core.event.TrainingProgramCreatedEvent;
import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.web.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Event listener for training program events.
 * Demonstrates how modules can communicate through events.
 */
@Component
public class ProgramEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ProgramEventListener.class);
    private final NotificationService notificationService;
    private final Tracer tracer;

    @Autowired
    public ProgramEventListener(NotificationService notificationService, Tracer tracer) {
        this.notificationService = notificationService;
        this.tracer = tracer;
    }

    /**
     * Handle the TrainingProgramCreatedEvent
     * 
     * @param event The event to handle
     */
    @EventListener
    public void handleProgramCreatedEvent(TrainingProgramCreatedEvent event) {
        TrainingProgram program = event.getProgram();
        logger.info("Training program created for week {} with max weight {}",
                program.getWeek(), program.getMaxWeight());
    }

    /**
     * Handle the ProgramsGeneratedEvent
     * 
     * @param event The event to handle
     */
    @EventListener
    public void handleProgramsGeneratedEvent(ProgramsGeneratedEvent event) {
        logger.info("User generated {} training programs for weeks {}-{} with max weight {}",
                event.getPrograms().size(), event.getFromWeek(), event.getToWeek(), event.getMaxWeight());

        // Create notification data
        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("programsCount", event.getPrograms().size());
        notificationData.put("fromWeek", event.getFromWeek());
        notificationData.put("toWeek", event.getToWeek());
        notificationData.put("maxWeight", event.getMaxWeight());

        // Add trace ID if available
        if (tracer.currentSpan() != null) {
            String traceId = tracer.currentSpan().context().traceIdString();
            notificationData.put("traceId", traceId);
            logger.debug("Adding trace ID {} to notification", traceId);
        }

        // Send notification to all connected clients
        notificationService.sendNotification("PROGRAM_GENERATED", notificationData);
    }
}
