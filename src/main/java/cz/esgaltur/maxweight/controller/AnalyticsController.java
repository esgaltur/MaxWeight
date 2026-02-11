package cz.esgaltur.maxweight.controller;

import cz.esgaltur.maxweight.model.WorkoutSession;
import cz.esgaltur.maxweight.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * REST controller for analytics endpoints.
 */
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsController.class);
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @PostMapping("/workout-sessions")
    public ResponseEntity<WorkoutSession> recordWorkoutSession(@RequestBody WorkoutSession session) {
        logger.info("Recording workout session: week {}, day {}", session.getWeekNumber(), session.getDayNumber());
        WorkoutSession saved = analyticsService.recordWorkoutSession(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @GetMapping("/workout-sessions")
    public ResponseEntity<List<WorkoutSession>> getWorkoutSessions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Integer weekNumber) {
        logger.info("Getting workout sessions: startDate={}, endDate={}, weekNumber={}", startDate, endDate, weekNumber);
        List<WorkoutSession> sessions = analyticsService.getWorkoutSessions(startDate, endDate, weekNumber);
        return ResponseEntity.ok(sessions);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getOverallStats() {
        logger.info("Getting overall statistics");
        Map<String, Object> stats = analyticsService.getOverallStats();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/progress")
    public ResponseEntity<Map<String, Object>> getProgressData(
            @RequestParam(defaultValue = "month") String period,
            @RequestParam(defaultValue = "volume") String metric) {
        logger.info("Getting progress data: period={}, metric={}", period, metric);
        Map<String, Object> progress = analyticsService.getProgressData(period, metric);
        return ResponseEntity.ok(progress);
    }
    
    @GetMapping("/personal-records")
    public ResponseEntity<Map<String, Object>> getPersonalRecords() {
        logger.info("Getting personal records");
        Map<String, Object> records = analyticsService.getPersonalRecords();
        return ResponseEntity.ok(records);
    }
}