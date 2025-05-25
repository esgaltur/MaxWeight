package cz.esgaltur.maxweight.web.controller;

import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.web.service.ProgramGenerationService;
import cz.esgaltur.maxweight.web.service.ProgramValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ApiController class.
 */
 class ApiControllerTest {

    @Mock
    private ProgramValidationService mockValidationService;

    @Mock
    private ProgramGenerationService mockGenerationService;

    @Mock
    private TrainingProgram mockProgram;

    private ApiController apiController;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        apiController = new ApiController(mockValidationService, mockGenerationService);
    }

    /**
     * Test the getProgram method with valid input.
     */
    @Test
     void testGetProgramWithValidInput() {
        // Arrange
        int weekNumber = 3;
        int maxWeight = 100;

        when(mockValidationService.isValidWeek(weekNumber)).thenReturn(true);
        when(mockValidationService.isValidMaxWeight(maxWeight)).thenReturn(true);
        when(mockGenerationService.generateProgram(weekNumber, maxWeight)).thenReturn(mockProgram);

        // Act
        ResponseEntity<?> response = apiController.getProgram(weekNumber, maxWeight);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgram, response.getBody());
        verify(mockValidationService).isValidWeek(weekNumber);
        verify(mockValidationService).isValidMaxWeight(maxWeight);
        verify(mockGenerationService).generateProgram(weekNumber, maxWeight);
    }

    /**
     * Test the getProgram method with invalid week number.
     */
    @Test
     void testGetProgramWithInvalidWeekNumber() {
        // Arrange
        int weekNumber = 0;
        int maxWeight = 100;

        when(mockValidationService.isValidWeek(weekNumber)).thenReturn(false);
        when(mockValidationService.getInvalidWeekMessage()).thenReturn("Invalid week number");

        // Act
        ResponseEntity<?> response = apiController.getProgram(weekNumber, maxWeight);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, String> errorMap = (Map<String, String>) response.getBody();
        assertTrue(errorMap.containsKey("error"));
        assertTrue(errorMap.get("error").contains("Invalid week number"));

        verify(mockValidationService).isValidWeek(weekNumber);
        verify(mockValidationService).getInvalidWeekMessage();
        verifyNoInteractions(mockGenerationService);
    }

    /**
     * Test the getProgram method with invalid max weight.
     */
    @Test
     void testGetProgramWithInvalidMaxWeight() {
        // Arrange
        int weekNumber = 3;
        int maxWeight = 0;

        when(mockValidationService.isValidWeek(weekNumber)).thenReturn(true);
        when(mockValidationService.isValidMaxWeight(maxWeight)).thenReturn(false);
        when(mockValidationService.getInvalidMaxWeightMessage()).thenReturn("Max weight must be greater than 0");

        // Act
        ResponseEntity<?> response = apiController.getProgram(weekNumber, maxWeight);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, String> errorMap = (Map<String, String>) response.getBody();
        assertTrue(errorMap.containsKey("error"));
        assertTrue(errorMap.get("error").contains("Max weight must be greater than 0"));

        verify(mockValidationService).isValidWeek(weekNumber);
        verify(mockValidationService).isValidMaxWeight(maxWeight);
        verify(mockValidationService).getInvalidMaxWeightMessage();
        verifyNoInteractions(mockGenerationService);
    }

    /**
     * Test the getPrograms method with valid input.
     */
    @Test
     void testGetProgramsWithValidInput() {
        // Arrange
        int fromWeek = 2;
        int toWeek = 4;
        int maxWeight = 100;

        List<TrainingProgram> programs = new ArrayList<>();
        programs.add(mockProgram);
        programs.add(mockProgram);
        programs.add(mockProgram);

        when(mockValidationService.isValidWeekRange(fromWeek, toWeek)).thenReturn(true);
        when(mockValidationService.isValidMaxWeight(maxWeight)).thenReturn(true);
        when(mockGenerationService.generatePrograms(fromWeek, toWeek, maxWeight)).thenReturn(programs);

        // Act
        ResponseEntity<?> response = apiController.getPrograms(fromWeek, toWeek, maxWeight);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(List.class, response.getBody());
        List<?> responsePrograms = (List<?>) response.getBody();
        assertEquals(3, responsePrograms.size()); // Should have 3 programs

        verify(mockValidationService).isValidWeekRange(fromWeek, toWeek);
        verify(mockValidationService).isValidMaxWeight(maxWeight);
        verify(mockGenerationService).generatePrograms(fromWeek, toWeek, maxWeight);
    }

    /**
     * Test the getPrograms method with invalid week range.
     */
    @Test
     void testGetProgramsWithInvalidWeekRange() {
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
        when(mockValidationService.isValidWeekRange(fromWeek, toWeek)).thenReturn(false);
        when(mockValidationService.getInvalidWeekRangeMessage()).thenReturn("Invalid week range");

        // Act
        ResponseEntity<?> response = apiController.getPrograms(fromWeek, toWeek, maxWeight);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertInstanceOf(Map.class, response.getBody());
        Map<String, String> errorMap = (Map<String, String>) response.getBody();
        assertTrue(errorMap.containsKey("error"));
        assertTrue(errorMap.get("error").contains("Invalid week range"));

        verify(mockValidationService).isValidWeekRange(fromWeek, toWeek);
        verify(mockValidationService).getInvalidWeekRangeMessage();
        verifyNoInteractions(mockGenerationService);

        // Reset mocks for next test
        reset(mockValidationService);
    }

    /**
     * Test the getPrograms method with invalid max weight.
     */
    @Test
     void testGetProgramsWithInvalidMaxWeight() {
        // Test cases for invalid max weight
        testInvalidMaxWeight(2, 4, 0); // maxWeight = 0
        testInvalidMaxWeight(2, 4, -10); // maxWeight < 0
    }

    /**
     * Helper method to test invalid max weight.
     */
    private void testInvalidMaxWeight(int fromWeek, int toWeek, int maxWeight) {
        // Arrange
        when(mockValidationService.isValidWeekRange(fromWeek, toWeek)).thenReturn(true);
        when(mockValidationService.isValidMaxWeight(maxWeight)).thenReturn(false);
        when(mockValidationService.getInvalidMaxWeightMessage()).thenReturn("Max weight must be greater than 0");

        // Act
        ResponseEntity<?> response = apiController.getPrograms(fromWeek, toWeek, maxWeight);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertInstanceOf(Map.class, response.getBody());
        Map<String, String> errorMap = (Map<String, String>) response.getBody();
        assertTrue(errorMap.containsKey("error"));
        assertTrue(errorMap.get("error").contains("Max weight must be greater than 0"));

        verify(mockValidationService).isValidWeekRange(fromWeek, toWeek);
        verify(mockValidationService).isValidMaxWeight(maxWeight);
        verify(mockValidationService).getInvalidMaxWeightMessage();
        verifyNoInteractions(mockGenerationService);

        // Reset mocks for next test
        reset(mockValidationService);
    }
}
