package cz.esgaltur.maxweight.web.controller;

import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.web.dto.ProgramRangeRequest;
import cz.esgaltur.maxweight.web.service.ProgramGenerationService;
import cz.esgaltur.maxweight.web.validation.ValidWeek;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API controller for the MaxWeight application.
 * Provides endpoints for accessing training program data programmatically.
 */
@RestController
@RequestMapping("/api")
@Validated
public class ApiController {

    private final ProgramGenerationService generationService;

    @Autowired
    public ApiController(ProgramGenerationService generationService) {
        this.generationService = generationService;
    }

    /**
     * Get a single training program for a specific week
     * 
     * @param weekNumber The week number (validated against {@link cz.esgaltur.maxweight.core.model.Week})
     * @param maxWeight The maximum weight for bench press
     * @return The training program
     */
    @GetMapping("/program/{weekNumber}")
    public ResponseEntity<TrainingProgram> getProgram(
            @PathVariable @ValidWeek int weekNumber,
            @RequestParam @Min(1) int maxWeight) {
        // Generate program
        TrainingProgram program = generationService.generateProgram(weekNumber, maxWeight);

        return ResponseEntity.ok(program);
    }

    /**
     * Get multiple training programs for a range of weeks
     * 
     * @param request The validated program range request
     * @return The list of training programs
     */
    @GetMapping("/programs")
    public ResponseEntity<List<TrainingProgram>> getPrograms(
            @Valid ProgramRangeRequest request) {
        // Generate programs
        List<TrainingProgram> programs = generationService.generatePrograms(
            request.getFromWeek(),
            request.getToWeek(),
            request.getMaxWeight()
        );

        return ResponseEntity.ok(programs);
    }
}
