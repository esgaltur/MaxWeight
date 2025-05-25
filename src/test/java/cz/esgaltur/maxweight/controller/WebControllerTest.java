package cz.esgaltur.maxweight.controller;

import cz.esgaltur.maxweight.model.TrainingProgram;
import cz.esgaltur.maxweight.model.Week;
import cz.esgaltur.maxweight.service.ProgramFactory;
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
    private ProgramFactory mockProgramFactory;

    @Mock
    private Model mockModel;

    @Mock
    private TrainingProgram mockProgram;

    private WebController webController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webController = new WebController(mockProgramFactory);
    }

    /**
     * Test the home method.
     */
    @Test
    public void testHome() {
        // Act
        String viewName = webController.home(mockModel);

        // Assert
        assertEquals("index", viewName);
        verify(mockModel).addAttribute("title", "MaxWeight - Bench Press Training Program Generator");
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

        List<TrainingProgram> programs = new ArrayList<>();
        when(mockProgramFactory.createProgram(Week.WEEK2, maxWeight)).thenReturn(mockProgram);
        when(mockProgramFactory.createProgram(Week.WEEK3, maxWeight)).thenReturn(mockProgram);
        when(mockProgramFactory.createProgram(Week.WEEK4, maxWeight)).thenReturn(mockProgram);

        // Act
        String viewName = webController.generateProgram(fromWeek, toWeek, maxWeight, mockModel);

        // Assert
        assertEquals("result", viewName);
        verify(mockProgramFactory).createProgram(Week.WEEK2, maxWeight);
        verify(mockProgramFactory).createProgram(Week.WEEK3, maxWeight);
        verify(mockProgramFactory).createProgram(Week.WEEK4, maxWeight);
        verify(mockModel).addAttribute(eq("programs"), anyList());
        verify(mockModel).addAttribute("maxWeight", maxWeight);
        verify(mockModel).addAttribute("fromWeek", fromWeek);
        verify(mockModel).addAttribute("toWeek", toWeek);
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
        reset(mockModel); // Reset mock to clear previous interactions

        // Act
        String viewName = webController.generateProgram(fromWeek, toWeek, maxWeight, mockModel);

        // Assert
        assertEquals("index", viewName);
        verify(mockModel).addAttribute(eq("error"), argThat(s -> s.toString().contains("Invalid week range")));
        verifyNoMoreInteractions(mockProgramFactory); // Ensure factory is not called
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
        reset(mockModel); // Reset mock to clear previous interactions

        // Act
        String viewName = webController.generateProgram(fromWeek, toWeek, maxWeight, mockModel);

        // Assert
        assertEquals("index", viewName);
        verify(mockModel).addAttribute(eq("error"), argThat(s -> s.toString().contains("Max weight must be greater than 0")));
        verifyNoMoreInteractions(mockProgramFactory); // Ensure factory is not called
    }
}
