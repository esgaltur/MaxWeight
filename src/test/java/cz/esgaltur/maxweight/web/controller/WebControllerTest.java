package cz.esgaltur.maxweight.web.controller;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.web.service.ProgramGenerationService;
import cz.esgaltur.maxweight.web.service.ProgramValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the WebController class.
 */
public class WebControllerTest {

    @Mock
    private ProgramValidationService mockValidationService;

    @Mock
    private ProgramGenerationService mockGenerationService;

    @Mock
    private Model mockModel;

    @Mock
    private TrainingProgram mockProgram;

    @Mock
    private Tracer mockTracer;

    @Mock
    private Span mockSpan;

    @Mock
    private TraceContext mockTraceContext;

    private WebController webController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webController = new WebController(mockValidationService, mockGenerationService, mockTracer);
    }

    /**
     * Test the home method.
     */
    @Test
    public void testHome() {
        // Arrange
        String traceId = "test-trace-id";
        when(mockTracer.currentSpan()).thenReturn(mockSpan);
        when(mockSpan.context()).thenReturn(mockTraceContext);
        when(mockTraceContext.traceIdString()).thenReturn(traceId);

        // Act
        String viewName = webController.home(mockModel);

        // Assert
        assertEquals("index", viewName);
        verify(mockModel).addAttribute("title", "MaxWeight - Bench Press Training Program Generator");
        verify(mockModel).addAttribute("traceId", traceId);
    }

    /**
     * Test the generateProgram method with valid input.
     */
    @Test
    public void testGenerateProgramWithValidInput() {
        // Arrange
        int fromWeek = 2;
        int toWeek = 4;
        int maxWeight = 100;
        String traceId = "test-trace-id";

        List<TrainingProgram> programs = new ArrayList<>();
        programs.add(mockProgram);
        programs.add(mockProgram);
        programs.add(mockProgram);

        when(mockValidationService.isValidWeekRange(fromWeek, toWeek)).thenReturn(true);
        when(mockValidationService.isValidMaxWeight(maxWeight)).thenReturn(true);
        when(mockGenerationService.generatePrograms(fromWeek, toWeek, maxWeight)).thenReturn(programs);
        when(mockTracer.currentSpan()).thenReturn(mockSpan);
        when(mockSpan.context()).thenReturn(mockTraceContext);
        when(mockTraceContext.traceIdString()).thenReturn(traceId);

        // Act
        String viewName = webController.generateProgram(fromWeek, toWeek, maxWeight, mockModel);

        // Assert
        assertEquals("result", viewName);
        verify(mockValidationService).isValidWeekRange(fromWeek, toWeek);
        verify(mockValidationService).isValidMaxWeight(maxWeight);
        verify(mockGenerationService).generatePrograms(fromWeek, toWeek, maxWeight);
        verify(mockModel).addAttribute("programs", programs);
        verify(mockModel).addAttribute("maxWeight", maxWeight);
        verify(mockModel).addAttribute("fromWeek", fromWeek);
        verify(mockModel).addAttribute("toWeek", toWeek);
        verify(mockModel).addAttribute("traceId", traceId);
    }

    /**
     * Test the generateProgram method with invalid week range.
     */
    @Test
    public void testGenerateProgramWithInvalidWeekRange() {
        // Test cases for invalid week range
        testInvalidWeekRange(0, 4, 100); // fromWeek < 1
        testInvalidWeekRange(7, 4, 100); // fromWeek > 6
        testInvalidWeekRange(2, 0, 100); // toWeek < 1
        testInvalidWeekRange(2, 7, 100); // toWeek > 6
        testInvalidWeekRange(4, 2, 100); // fromWeek > toWeek
    }

    /**
     * Helper method to test invalid week range.
     */
    private void testInvalidWeekRange(int fromWeek, int toWeek, int maxWeight) {
        // Arrange
        reset(mockModel, mockValidationService); // Reset mocks to clear previous interactions
        when(mockValidationService.isValidWeekRange(fromWeek, toWeek)).thenReturn(false);
        when(mockValidationService.getInvalidWeekRangeMessage()).thenReturn("Invalid week range");

        // Act
        String viewName = webController.generateProgram(fromWeek, toWeek, maxWeight, mockModel);

        // Assert
        assertEquals("index", viewName);
        verify(mockValidationService).isValidWeekRange(fromWeek, toWeek);
        verify(mockValidationService).getInvalidWeekRangeMessage();
        verify(mockModel).addAttribute(eq("error"), eq("Invalid week range"));
        verifyNoInteractions(mockGenerationService); // Ensure generation service is not called
    }

    /**
     * Test the generateProgram method with invalid max weight.
     */
    @Test
    public void testGenerateProgramWithInvalidMaxWeight() {
        // Test cases for invalid max weight
        testInvalidMaxWeight(2, 4, 0); // maxWeight = 0
        testInvalidMaxWeight(2, 4, -10); // maxWeight < 0
    }

    /**
     * Helper method to test invalid max weight.
     */
    private void testInvalidMaxWeight(int fromWeek, int toWeek, int maxWeight) {
        // Arrange
        reset(mockModel, mockValidationService); // Reset mocks to clear previous interactions
        when(mockValidationService.isValidWeekRange(fromWeek, toWeek)).thenReturn(true);
        when(mockValidationService.isValidMaxWeight(maxWeight)).thenReturn(false);
        when(mockValidationService.getInvalidMaxWeightMessage()).thenReturn("Max weight must be greater than 0");

        // Act
        String viewName = webController.generateProgram(fromWeek, toWeek, maxWeight, mockModel);

        // Assert
        assertEquals("index", viewName);
        verify(mockValidationService).isValidWeekRange(fromWeek, toWeek);
        verify(mockValidationService).isValidMaxWeight(maxWeight);
        verify(mockValidationService).getInvalidMaxWeightMessage();
        verify(mockModel).addAttribute(eq("error"), eq("Max weight must be greater than 0"));
        verifyNoInteractions(mockGenerationService); // Ensure generation service is not called
    }
}
