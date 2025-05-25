package cz.esgaltur.maxweight.web.service;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Service for managing Server-Sent Events (SSE) connections and sending notifications.
 */
@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    /**
     * Add a new SSE emitter for a client connection.
     *
     * @return a new SseEmitter instance
     */
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        // Add the emitter to the list
        this.emitters.add(emitter);

        // Remove the emitter when the connection is completed or times out
        emitter.onCompletion(() -> {
            this.emitters.remove(emitter);
            logger.debug("SSE connection completed");
        });

        emitter.onTimeout(() -> {
            emitter.complete();
            this.emitters.remove(emitter);
            logger.debug("SSE connection timed out");
        });

        emitter.onError(e -> {
            emitter.complete();
            this.emitters.remove(emitter);
            // Client disconnection is a normal event, log at debug level
            if (e instanceof ClientAbortException) {
                logger.debug("Client aborted SSE connection", e);
            } else if (e instanceof IOException) {
                logger.debug("Client disconnected from SSE stream", e);
            } else {
                logger.error("SSE connection error", e);
            }
        });

        // Send an initial ping event to establish the connection
        try {
            emitter.send(SseEmitter.event().name("PING").data("Connection established"));
            logger.debug("SSE connection established");
        } catch (ClientAbortException e) {
            emitter.complete();
            this.emitters.remove(emitter);
            // Client abort is a normal event, log at debug level
            logger.debug("Client aborted connection during initial SSE connection", e);
        } catch (IOException e) {
            emitter.completeWithError(e);
            // Client disconnection is a normal event, log at debug level
            logger.debug("Client disconnected during initial SSE connection", e);
        }

        return emitter;
    }

    /**
     * Send a notification to all connected clients.
     *
     * @param eventName the name of the event
     * @param eventData the data to send with the event
     */
    public void sendNotification(String eventName, Object eventData) {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(eventData));
                logger.debug("Sent notification: {} to an emitter", eventName);
            } catch (ClientAbortException e) {
                deadEmitters.add(emitter);
                // Client abort is a normal event, log at debug level
                logger.debug("Client aborted connection during notification", e);
            } catch (IOException e) {
                deadEmitters.add(emitter);
                // All IOExceptions in this context are likely client disconnections
                // No need to check specific message strings as the web server already handles this
                logger.debug("Client disconnected from SSE stream", e);
            }
        });

        // Remove dead emitters
        emitters.removeAll(deadEmitters);
    }
}
