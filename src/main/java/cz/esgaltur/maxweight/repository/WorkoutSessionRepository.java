package cz.esgaltur.maxweight.repository;

import cz.esgaltur.maxweight.model.WorkoutSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for WorkoutSession entity.
 */
@Repository
public interface WorkoutSessionRepository extends MongoRepository<WorkoutSession, String> {
    
    /**
     * Find all workout sessions for a user.
     *
     * @param userId User ID
     * @return List of workout sessions
     */
    List<WorkoutSession> findByUserIdOrderByCompletedAtDesc(String userId);
    
    /**
     * Find workout sessions for a user within a date range.
     *
     * @param userId User ID
     * @param startDate Start date
     * @param endDate End date
     * @return List of workout sessions
     */
    List<WorkoutSession> findByUserIdAndCompletedAtBetweenOrderByCompletedAtAsc(
            String userId, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find workout sessions for a specific week.
     *
     * @param userId User ID
     * @param weekNumber Week number
     * @return List of workout sessions
     */
    List<WorkoutSession> findByUserIdAndWeekNumber(String userId, Integer weekNumber);
    
    /**
     * Count total workout sessions for a user.
     *
     * @param userId User ID
     * @return Count of sessions
     */
    Long countByUserId(String userId);
    
    /**
     * Find the most recent workout session for a user.
     *
     * @param userId User ID
     * @return Most recent workout session
     */
    WorkoutSession findFirstByUserIdOrderByCompletedAtDesc(String userId);
    
    /**
     * Get total volume lifted by user in date range.
     *
     * @param userId User ID
     * @param startDate Start date
     * @param endDate End date
     * @return Sum of total volume
     */
    @Query(value = "{ 'userId': ?0, 'completedAt': { $gte: ?1, $lte: ?2 } }", 
           fields = "{ 'totalVolume': 1 }")
    List<WorkoutSession> findVolumeByUserIdAndDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate);
}