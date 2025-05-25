package cz.esgaltur.maxweight.controller;

import cz.esgaltur.maxweight.model.TrainingProgram;
import cz.esgaltur.maxweight.model.Week;
import cz.esgaltur.maxweight.service.ProgramFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    private final ProgramFactory programFactory;

    @Autowired
    public ApiController(ProgramFactory programFactory) {
        this.programFactory = programFactory;
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
        if (weekNumber < 1 || weekNumber > 6) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid week number. Please select a week between 1 and 6.");
            return ResponseEntity.badRequest().body(error);
        }
        
        if (maxWeight <= 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Max weight must be greater than 0.");
            return ResponseEntity.badRequest().body(error);
        }
        
        // Generate program
        Week week = Week.fromWeekNumber(weekNumber);
        TrainingProgram program = programFactory.createProgram(week, maxWeight);
        
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
        if (fromWeek < 1 || fromWeek > 6 || toWeek < 1 || toWeek > 6 || fromWeek > toWeek) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid week range. Please select weeks between 1 and 6, with 'from' week less than or equal to 'to' week.");
            return ResponseEntity.badRequest().body(error);
        }
        
        if (maxWeight <= 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Max weight must be greater than 0.");
            return ResponseEntity.badRequest().body(error);
        }
        
        // Generate programs
        List<TrainingProgram> programs = new ArrayList<>();
        for (int weekNumber = fromWeek; weekNumber <= toWeek; weekNumber++) {
            Week week = Week.fromWeekNumber(weekNumber);
            TrainingProgram program = programFactory.createProgram(week, maxWeight);
            programs.add(program);
        }
        
        return ResponseEntity.ok(programs);
    }
}