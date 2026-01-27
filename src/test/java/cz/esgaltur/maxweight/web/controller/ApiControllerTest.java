package cz.esgaltur.maxweight.web.controller;

import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.web.dto.ProgramRangeRequest;
import cz.esgaltur.maxweight.web.service.ProgramGenerationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ApiController class.
 */
class ApiControllerTest {

    @Mock
    private ProgramGenerationService mockGenerationService;

    @Mock
    private TrainingProgram mockProgram;

    private ApiController apiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        apiController = new ApiController(mockGenerationService);
    }

    /**
     * Test the getProgram method with valid input.
     */
    @Test
    void testGetProgramWithValidInput() {
        // Arrange
        int weekNumber = 3;
        int maxWeight = 100;

        when(mockGenerationService.generateProgram(weekNumber, maxWeight)).thenReturn(mockProgram);

        // Act
        ResponseEntity<TrainingProgram> response = apiController.getProgram(weekNumber, maxWeight);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgram, response.getBody());
        verify(mockGenerationService).generateProgram(weekNumber, maxWeight);
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
        ProgramRangeRequest request = new ProgramRangeRequest();
        request.setFromWeek(fromWeek);
        request.setToWeek(toWeek);
        request.setMaxWeight(maxWeight);

        List<TrainingProgram> programs = new ArrayList<>();
        programs.add(mockProgram);
        programs.add(mockProgram);
        programs.add(mockProgram);

        when(mockGenerationService.generatePrograms(fromWeek, toWeek, maxWeight)).thenReturn(programs);

        // Act
        ResponseEntity<List<TrainingProgram>> response = apiController.getPrograms(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(List.class, response.getBody());
        List<?> responsePrograms = response.getBody();
        assertEquals(3, responsePrograms.size()); // Should have 3 programs

        verify(mockGenerationService).generatePrograms(fromWeek, toWeek, maxWeight);
    }
}
