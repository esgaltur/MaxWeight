package cz.esgaltur.maxweight.web.controller;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.web.dto.ProgramRangeRequest;
import cz.esgaltur.maxweight.web.service.ProgramGenerationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    @Mock
    private BindingResult mockBindingResult;

    private WebController webController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webController = new WebController(mockGenerationService, mockTracer);
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
        ProgramRangeRequest request = new ProgramRangeRequest();
        request.setFromWeek(2);
        request.setToWeek(4);
        request.setMaxWeight(100);
        String traceId = "test-trace-id";

        List<TrainingProgram> programs = new ArrayList<>();
        programs.add(mockProgram);
        programs.add(mockProgram);
        programs.add(mockProgram);

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockGenerationService.generatePrograms(2, 4, 100)).thenReturn(programs);
        when(mockTracer.currentSpan()).thenReturn(mockSpan);
        when(mockSpan.context()).thenReturn(mockTraceContext);
        when(mockTraceContext.traceIdString()).thenReturn(traceId);

        // Act
        String viewName = webController.generateProgram(request, mockBindingResult, mockModel);

        // Assert
        assertEquals("result", viewName);
        verify(mockGenerationService).generatePrograms(2, 4, 100);
        verify(mockModel).addAttribute("programs", programs);
        verify(mockModel).addAttribute("maxWeight", 100);
        verify(mockModel).addAttribute("fromWeek", 2);
        verify(mockModel).addAttribute("toWeek", 4);
        verify(mockModel).addAttribute("traceId", traceId);
    }

    /**
     * Test the generateProgram method with validation errors.
     */
    @Test
    public void testGenerateProgramWithErrors() {
        // Arrange
        ProgramRangeRequest request = new ProgramRangeRequest();
        ObjectError error = new ObjectError("programRange", "Invalid input");

        when(mockBindingResult.hasErrors()).thenReturn(true);
        when(mockBindingResult.getAllErrors()).thenReturn(List.of(error));

        // Act
        String viewName = webController.generateProgram(request, mockBindingResult, mockModel);

        // Assert
        assertEquals("index", viewName);
        verify(mockModel).addAttribute("error", "Invalid input");
        verifyNoInteractions(mockGenerationService);
    }
}
