package cz.esgaltur.maxweight.web.controller;

import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.web.service.ProgramGenerationService;
import cz.esgaltur.maxweight.web.service.ProgramValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API controller for the MaxWeight application.
 * Provides endpoints for accessing training program data programmatically.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final ProgramValidationService validationService;
    private final ProgramGenerationService generationService;

    @Autowired
    public ApiController(ProgramValidationService validationService, ProgramGenerationService generationService) {
        this.validationService = validationService;
        this.generationService = generationService;
    }

    /**
     * Get a single training program for a specific week
     * 
     * @param weekNumber The week number (1-6)
     * @param maxWeight The maximum weight for bench press
     * @return The training program
     */
    @GetMapping("/program/{weekNumber}")
    public ResponseEntity<?> getProgram(
            @PathVariable int weekNumber,
            @RequestParam int maxWeight) {

        // Validate input
        if (!validationService.isValidWeek(weekNumber)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", validationService.getInvalidWeekMessage());
            return ResponseEntity.badRequest().body(error);
        }

        if (!validationService.isValidMaxWeight(maxWeight)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", validationService.getInvalidMaxWeightMessage());
            return ResponseEntity.badRequest().body(error);
        }

        // Generate program
        TrainingProgram program = generationService.generateProgram(weekNumber, maxWeight);

        return ResponseEntity.ok(program);
    }

    /**
     * Get multiple training programs for a range of weeks
     * 
     * @param fromWeek The starting week number
     * @param toWeek The ending week number
     * @param maxWeight The maximum weight for bench press
     * @return The list of training programs
     */
    @GetMapping("/programs")
    public ResponseEntity<?> getPrograms(
            @RequestParam int fromWeek,
            @RequestParam int toWeek,
            @RequestParam int maxWeight) {

        // Validate input
        if (!validationService.isValidWeekRange(fromWeek, toWeek)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", validationService.getInvalidWeekRangeMessage());
            return ResponseEntity.badRequest().body(error);
        }

        if (!validationService.isValidMaxWeight(maxWeight)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", validationService.getInvalidMaxWeightMessage());
            return ResponseEntity.badRequest().body(error);
        }

        // Generate programs
        List<TrainingProgram> programs = generationService.generatePrograms(fromWeek, toWeek, maxWeight);

        return ResponseEntity.ok(programs);
    }
}
