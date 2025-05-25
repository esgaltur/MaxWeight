package cz.esgaltur.maxweight.web.listener;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import cz.esgaltur.maxweight.core.event.ProgramsGeneratedEvent;
import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.core.model.Week;
import cz.esgaltur.maxweight.web.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ProgramEventListener class.
 */
public class ProgramEventListenerTest {

    @Mock
    private NotificationService mockNotificationService;

    @Mock
    private Tracer mockTracer;

    @Mock
    private Span mockSpan;

    @Mock
    private TraceContext mockTraceContext;

    private ProgramEventListener programEventListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        programEventListener = new ProgramEventListener(mockNotificationService, mockTracer);
    }

    /**
     * Test that the ProgramEventListener sends a notification when a ProgramsGeneratedEvent is received.
     */
    @Test
    public void testHandleProgramsGeneratedEvent() {
        // Arrange
        int fromWeek = 2;
        int toWeek = 4;
        int maxWeight = 100;
        String testTraceId = "test-trace-id";

        List<TrainingProgram> programs = new ArrayList<>();
        programs.add(new TrainingProgram(Week.WEEK2, maxWeight));
        programs.add(new TrainingProgram(Week.WEEK3, maxWeight));
        programs.add(new TrainingProgram(Week.WEEK4, maxWeight));

        ProgramsGeneratedEvent event = new ProgramsGeneratedEvent(programs, maxWeight, fromWeek, toWeek);

        // Set up mock Tracer to return a trace ID
        when(mockTracer.currentSpan()).thenReturn(mockSpan);
        when(mockSpan.context()).thenReturn(mockTraceContext);
        when(mockTraceContext.traceIdString()).thenReturn(testTraceId);

        // Act
        programEventListener.handleProgramsGeneratedEvent(event);

        // Assert
        ArgumentCaptor<Map<String, Object>> notificationDataCaptor = ArgumentCaptor.forClass(Map.class);
        verify(mockNotificationService).sendNotification(eq("PROGRAM_GENERATED"), notificationDataCaptor.capture());

        Map<String, Object> notificationData = notificationDataCaptor.getValue();
        assertEquals(3, notificationData.get("programsCount"));
        assertEquals(fromWeek, notificationData.get("fromWeek"));
        assertEquals(toWeek, notificationData.get("toWeek"));
        assertEquals(maxWeight, notificationData.get("maxWeight"));
        assertEquals(testTraceId, notificationData.get("traceId"));
    }
}
