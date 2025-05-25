package cz.esgaltur.maxweight.controller;

import cz.esgaltur.maxweight.model.TrainingProgram;
import cz.esgaltur.maxweight.model.Week;
import cz.esgaltur.maxweight.service.ProgramFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ApiController class.
 */
public class ApiControllerTest {

    @Mock
    private ProgramFactory mockProgramFactory;

    @Mock
    private TrainingProgram mockProgram;

    private ApiController apiController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        apiController = new ApiController(mockProgramFactory);
    }

    /**
     * Test the getProgram method with valid input.
     */
    @Test
    public void testGetProgramWithValidInput() {
        // Arrange
        int weekNumber = 3;
        int maxWeight = 100;
        when(mockProgramFactory.createProgram(Week.WEEK3, maxWeight)).thenReturn(mockProgram);

        // Act
        ResponseEntity<?> response = apiController.getProgram(weekNumber, maxWeight);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgram, response.getBody());
        verify(mockProgramFactory).createProgram(Week.WEEK3, maxWeight);
    }

    /**
     * Test the getProgram method with invalid week number.
     */
    @Test
    public void testGetProgramWithInvalidWeekNumber() {
        // Test with week number less than 1
        ResponseEntity<?> response1 = apiController.getProgram(0, 100);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertTrue(response1.getBody() instanceof Map);
        Map<String, String> errorMap1 = (Map<String, String>) response1.getBody();
        assertTrue(errorMap1.containsKey("error"));
        assertTrue(errorMap1.get("error").contains("Invalid week number"));

        // Test with week number greater than 6
        ResponseEntity<?> response2 = apiController.getProgram(7, 100);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertTrue(response2.getBody() instanceof Map);
        Map<String, String> errorMap2 = (Map<String, String>) response2.getBody();
        assertTrue(errorMap2.containsKey("error"));
        assertTrue(errorMap2.get("error").contains("Invalid week number"));

        // Verify that the factory was not called
        verifyNoInteractions(mockProgramFactory);
    }

    /**
     * Test the getProgram method with invalid max weight.
     */
    @Test
    public void testGetProgramWithInvalidMaxWeight() {
        // Test with max weight = 0
        ResponseEntity<?> response1 = apiController.getProgram(3, 0);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertTrue(response1.getBody() instanceof Map);
        Map<String, String> errorMap1 = (Map<String, String>) response1.getBody();
        assertTrue(errorMap1.containsKey("error"));
        assertTrue(errorMap1.get("error").contains("Max weight must be greater than 0"));

        // Test with max weight < 0
        ResponseEntity<?> response2 = apiController.getProgram(3, -10);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertTrue(response2.getBody() instanceof Map);
        Map<String, String> errorMap2 = (Map<String, String>) response2.getBody();
        assertTrue(errorMap2.containsKey("error"));
        assertTrue(errorMap2.get("error").contains("Max weight must be greater than 0"));

        // Verify that the factory was not called
        verifyNoInteractions(mockProgramFactory);
    }

    /**
     * Test the getPrograms method with valid input.
     */
    @Test
    public void testGetProgramsWithValidInput() {
        // Arrange
        int fromWeek = 2;
        int toWeek = 4;
        int maxWeight = 100;

        when(mockProgramFactory.createProgram(Week.WEEK2, maxWeight)).thenReturn(mockProgram);
        when(mockProgramFactory.createProgram(Week.WEEK3, maxWeight)).thenReturn(mockProgram);
        when(mockProgramFactory.createProgram(Week.WEEK4, maxWeight)).thenReturn(mockProgram);

        // Act
        ResponseEntity<?> response = apiController.getPrograms(fromWeek, toWeek, maxWeight);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<?> programs = (List<?>) response.getBody();
        assertEquals(3, programs.size()); // Should have 3 programs (weeks 2, 3, 4)

        verify(mockProgramFactory).createProgram(Week.WEEK2, maxWeight);
        verify(mockProgramFactory).createProgram(Week.WEEK3, maxWeight);
        verify(mockProgramFactory).createProgram(Week.WEEK4, maxWeight);
    }

    /**
     * Test the getPrograms method with invalid week range.
     */
    @Test
    public void testGetProgramsWithInvalidWeekRange() {
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
        // Act
        ResponseEntity<?> response = apiController.getPrograms(fromWeek, toWeek, maxWeight);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, String> errorMap = (Map<String, String>) response.getBody();
        assertTrue(errorMap.containsKey("error"));
        assertTrue(errorMap.get("error").contains("Invalid week range"));

        // Verify that the factory was not called
        verifyNoInteractions(mockProgramFactory);
    }

    /**
     * Test the getPrograms method with invalid max weight.
     */
    @Test
    public void testGetProgramsWithInvalidMaxWeight() {
        // Test cases for invalid max weight
        testInvalidMaxWeight(2, 4, 0); // maxWeight = 0
        testInvalidMaxWeight(2, 4, -10); // maxWeight < 0
    }

    /**
     * Helper method to test invalid max weight.
     */
    private void testInvalidMaxWeight(int fromWeek, int toWeek, int maxWeight) {
        // Act
        ResponseEntity<?> response = apiController.getPrograms(fromWeek, toWeek, maxWeight);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, String> errorMap = (Map<String, String>) response.getBody();
        assertTrue(errorMap.containsKey("error"));
        assertTrue(errorMap.get("error").contains("Max weight must be greater than 0"));

        // Verify that the factory was not called
        verifyNoInteractions(mockProgramFactory);
    }
}