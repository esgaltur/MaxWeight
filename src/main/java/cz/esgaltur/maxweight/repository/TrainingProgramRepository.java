package cz.esgaltur.maxweight.repository;

import cz.esgaltur.maxweight.model.SavedTrainingProgram;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for SavedTrainingProgram entity.
 */
@Repository
public interface TrainingProgramRepository extends MongoRepository<SavedTrainingProgram, String> {
    
    /**
     * Find all training programs for a user.
     *
     * @param userId User ID
     * @return List of training programs
     */
    List<SavedTrainingProgram> findByUserId(String userId);
    
    /**
     * Find training program by user ID and week number.
     *
     * @param userId User ID
     * @param weekNumber Week number
     * @return Optional training program
     */
    Optional<SavedTrainingProgram> findByUserIdAndWeekNumber(String userId, Integer weekNumber);
    
    /**
     * Find training programs updated after a specific timestamp.
     *
     * @param userId User ID
     * @param updatedAt Timestamp
     * @return List of training programs
     */
    List<SavedTrainingProgram> findByUserIdAndUpdatedAtAfter(String userId, LocalDateTime updatedAt);
    
    /**
     * Delete all training programs for a user.
     *
     * @param userId User ID
     */
    void deleteByUserId(String userId);
}