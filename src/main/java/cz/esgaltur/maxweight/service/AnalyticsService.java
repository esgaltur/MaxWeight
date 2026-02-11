package cz.esgaltur.maxweight.service;

import cz.esgaltur.maxweight.model.User;
import cz.esgaltur.maxweight.model.WorkoutSession;
import cz.esgaltur.maxweight.repository.WorkoutSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for workout analytics and statistics.
 */
@Service
public class AnalyticsService {
    
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);
    
    @Autowired
    private WorkoutSessionRepository sessionRepository;
    
    @Autowired
    private UserService userService;
    
    /**
     * Record a completed workout session.
     *
     * @param session Workout session
     * @return Saved workout session
     */
    public WorkoutSession recordWorkoutSession(WorkoutSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        
        logger.info("Recording workout session for user: {}, week: {}, day: {}", 
                username, session.getWeekNumber(), session.getDayNumber());
        
        session.setUserId(user.getId());
        session.setCompletedAt(LocalDateTime.now());
        
        // Calculate statistics
        calculateSessionStats(session);
        
        WorkoutSession saved = sessionRepository.save(session);
        
        logger.info("Workout session recorded successfully: {}", saved.getId());
        
        return saved;
    }
    
    /**
     * Get all workout sessions for the current user.
     *
     * @param startDate Optional start date
     * @param endDate Optional end date
     * @param weekNumber Optional week number
     * @return List of workout sessions
     */
    public List<WorkoutSession> getWorkoutSessions(LocalDateTime startDate, LocalDateTime endDate, Integer weekNumber) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        
        logger.info("Getting workout sessions for user: {}", username);
        
        if (startDate != null && endDate != null) {
            return sessionRepository.findByUserIdAndCompletedAtBetweenOrderByCompletedAtAsc(
                    user.getId(), startDate, endDate);
        } else if (weekNumber != null) {
            return sessionRepository.findByUserIdAndWeekNumber(user.getId(), weekNumber);
        } else {
            return sessionRepository.findByUserIdOrderByCompletedAtDesc(user.getId());
        }
    }
    
    /**
     * Get overall workout statistics.
     *
     * @return Map of statistics
     */
    public Map<String, Object> getOverallStats() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        
        logger.info("Getting overall stats for user: {}", username);
        
        List<WorkoutSession> allSessions = sessionRepository.findByUserIdOrderByCompletedAtDesc(user.getId());
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalWorkouts", allSessions.size());
        
        double totalVolume = allSessions.stream()
                .mapToDouble(s -> s.getTotalVolume() != null ? s.getTotalVolume() : 0.0)
                .sum();
        stats.put("totalVolume", totalVolume);
        
        double avgDuration = allSessions.stream()
                .filter(s -> s.getDuration() != null)
                .mapToLong(WorkoutSession::getDuration)
                .average()
                .orElse(0.0);
        stats.put("averageWorkoutDuration", (long) avgDuration);
        
        stats.put("currentStreak", calculateCurrentStreak(allSessions));
        stats.put("longestStreak", calculateLongestStreak(allSessions));
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekStart = now.minusDays(7);
        long workoutsThisWeek = allSessions.stream()
                .filter(s -> s.getCompletedAt().isAfter(weekStart))
                .count();
        stats.put("workoutsThisWeek", workoutsThisWeek);
        
        LocalDateTime monthStart = now.minusDays(30);
        long workoutsThisMonth = allSessions.stream()
                .filter(s -> s.getCompletedAt().isAfter(monthStart))
                .count();
        stats.put("workoutsThisMonth", workoutsThisMonth);
        
        if (!allSessions.isEmpty()) {
            stats.put("lastWorkoutDate", allSessions.get(0).getCompletedAt());
        }
        
        return stats;
    }
    
    /**
     * Get progress data for visualization.
     *
     * @param period Period (week, month, year, all)
     * @param metric Metric to track (volume, weight, reps, duration)
     * @return Progress data
     */
    public Map<String, Object> getProgressData(String period, String metric) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        
        logger.info("Getting progress data for user: {}, period: {}, metric: {}", username, period, metric);
        
        LocalDateTime startDate = calculateStartDate(period);
        LocalDateTime endDate = LocalDateTime.now();
        
        List<WorkoutSession> sessions = sessionRepository.findByUserIdAndCompletedAtBetweenOrderByCompletedAtAsc(
                user.getId(), startDate, endDate);
        
        List<Map<String, Object>> dataPoints = sessions.stream()
                .map(session -> {
                    Map<String, Object> point = new HashMap<>();
                    point.put("date", session.getCompletedAt());
                    point.put("value", getMetricValue(session, metric));
                    return point;
                })
                .collect(Collectors.toList());
        
        Map<String, Object> result = new HashMap<>();
        result.put("period", period);
        result.put("metric", metric);
        result.put("dataPoints", dataPoints);
        result.put("trend", calculateTrend(dataPoints));
        result.put("percentageChange", calculatePercentageChange(dataPoints));
        
        return result;
    }
    
    /**
     * Get personal records.
     *
     * @return Personal records
     */
    public Map<String, Object> getPersonalRecords() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        
        logger.info("Getting personal records for user: {}", username);
        
        List<WorkoutSession> allSessions = sessionRepository.findByUserIdOrderByCompletedAtDesc(user.getId());
        
        Map<String, Object> records = new HashMap<>();
        
        // Max weight lifted in a single set
        double maxWeight = allSessions.stream()
                .flatMap(s -> s.getExercises().stream())
                .filter(e -> e.getActualWeight() != null)
                .mapToDouble(WorkoutSession.CompletedExercise::getActualWeight)
                .max()
                .orElse(0.0);
        records.put("maxWeightLifted", maxWeight);
        
        // Max volume in a single session
        double maxVolume = allSessions.stream()
                .filter(s -> s.getTotalVolume() != null)
                .mapToDouble(WorkoutSession::getTotalVolume)
                .max()
                .orElse(0.0);
        records.put("maxVolumeSession", maxVolume);
        
        // Max reps in a single set
        int maxReps = allSessions.stream()
                .flatMap(s -> s.getExercises().stream())
                .filter(e -> e.getActualReps() != null)
                .mapToInt(WorkoutSession.CompletedExercise::getActualReps)
                .max()
                .orElse(0);
        records.put("maxRepsInSet", maxReps);
        
        // Find record-setting sessions
        List<Map<String, Object>> recordSessions = allSessions.stream()
                .filter(s -> s.getTotalVolume() != null && s.getTotalVolume() >= maxVolume * 0.9)
                .limit(5)
                .map(s -> {
                    Map<String, Object> record = new HashMap<>();
                    record.put("weekNumber", s.getWeekNumber());
                    record.put("dayNumber", s.getDayNumber());
                    record.put("weight", s.getMaxWeight());
                    record.put("volume", s.getTotalVolume());
                    record.put("achievedAt", s.getCompletedAt());
                    return record;
                })
                .collect(Collectors.toList());
        records.put("records", recordSessions);
        
        return records;
    }
    
    /**
     * Calculate session statistics.
     */
    private void calculateSessionStats(WorkoutSession session) {
        if (session.getExercises() == null || session.getExercises().isEmpty()) {
            return;
        }
        
        session.setTotalSets(session.getExercises().size());
        
        int totalReps = session.getExercises().stream()
                .filter(e -> e.getActualReps() != null)
                .mapToInt(WorkoutSession.CompletedExercise::getActualReps)
                .sum();
        session.setTotalReps(totalReps);
        
        double totalVolume = session.getExercises().stream()
                .filter(e -> e.getActualWeight() != null && e.getActualReps() != null)
                .mapToDouble(e -> e.getActualWeight() * e.getActualReps())
                .sum();
        session.setTotalVolume(totalVolume);
    }
    
    /**
     * Calculate current workout streak.
     */
    private int calculateCurrentStreak(List<WorkoutSession> sessions) {
        if (sessions.isEmpty()) {
            return 0;
        }
        
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime lastWorkout = sessions.get(0).getCompletedAt().truncatedTo(ChronoUnit.DAYS);
        
        // Check if last workout was today or yesterday
        long daysSinceLastWorkout = ChronoUnit.DAYS.between(lastWorkout, today);
        if (daysSinceLastWorkout > 1) {
            return 0;
        }
        
        int streak = 1;
        for (int i = 1; i < sessions.size(); i++) {
            LocalDateTime current = sessions.get(i).getCompletedAt().truncatedTo(ChronoUnit.DAYS);
            LocalDateTime previous = sessions.get(i - 1).getCompletedAt().truncatedTo(ChronoUnit.DAYS);
            
            long daysBetween = ChronoUnit.DAYS.between(current, previous);
            if (daysBetween <= 1) {
                streak++;
            } else {
                break;
            }
        }
        
        return streak;
    }
    
    /**
     * Calculate longest workout streak.
     */
    private int calculateLongestStreak(List<WorkoutSession> sessions) {
        if (sessions.isEmpty()) {
            return 0;
        }
        
        int longestStreak = 1;
        int currentStreak = 1;
        
        for (int i = 1; i < sessions.size(); i++) {
            LocalDateTime current = sessions.get(i).getCompletedAt().truncatedTo(ChronoUnit.DAYS);
            LocalDateTime previous = sessions.get(i - 1).getCompletedAt().truncatedTo(ChronoUnit.DAYS);
            
            long daysBetween = ChronoUnit.DAYS.between(current, previous);
            if (daysBetween <= 1) {
                currentStreak++;
                longestStreak = Math.max(longestStreak, currentStreak);
            } else {
                currentStreak = 1;
            }
        }
        
        return longestStreak;
    }
    
    /**
     * Calculate start date based on period.
     */
    private LocalDateTime calculateStartDate(String period) {
        LocalDateTime now = LocalDateTime.now();
        switch (period.toLowerCase()) {
            case "week":
                return now.minusDays(7);
            case "month":
                return now.minusDays(30);
            case "year":
                return now.minusDays(365);
            case "all":
            default:
                return LocalDateTime.of(2000, 1, 1, 0, 0);
        }
    }
    
    /**
     * Get metric value from session.
     */
    private double getMetricValue(WorkoutSession session, String metric) {
        switch (metric.toLowerCase()) {
            case "volume":
                return session.getTotalVolume() != null ? session.getTotalVolume() : 0.0;
            case "weight":
                return session.getMaxWeight() != null ? session.getMaxWeight() : 0.0;
            case "reps":
                return session.getTotalReps() != null ? session.getTotalReps() : 0.0;
            case "duration":
                return session.getDuration() != null ? session.getDuration() : 0.0;
            default:
                return 0.0;
        }
    }
    
    /**
     * Calculate trend from data points.
     */
    private String calculateTrend(List<Map<String, Object>> dataPoints) {
        if (dataPoints.size() < 2) {
            return "stable";
        }
        
        double firstValue = (double) dataPoints.get(0).get("value");
        double lastValue = (double) dataPoints.get(dataPoints.size() - 1).get("value");
        
        if (lastValue > firstValue * 1.05) {
            return "increasing";
        } else if (lastValue < firstValue * 0.95) {
            return "decreasing";
        } else {
            return "stable";
        }
    }
    
    /**
     * Calculate percentage change from data points.
     */
    private double calculatePercentageChange(List<Map<String, Object>> dataPoints) {
        if (dataPoints.size() < 2) {
            return 0.0;
        }
        
        double firstValue = (double) dataPoints.get(0).get("value");
        double lastValue = (double) dataPoints.get(dataPoints.size() - 1).get("value");
        
        if (firstValue == 0) {
            return 0.0;
        }
        
        return ((lastValue - firstValue) / firstValue) * 100.0;
    }
}