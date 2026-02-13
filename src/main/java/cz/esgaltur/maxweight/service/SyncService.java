package cz.esgaltur.maxweight.service;

import cz.esgaltur.maxweight.api.model.*;
import cz.esgaltur.maxweight.model.SavedTrainingProgram;
import cz.esgaltur.maxweight.model.User;
import cz.esgaltur.maxweight.repository.TrainingProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for synchronizing training programs with mobile clients.
 */
@Service
public class SyncService {
    
    private static final Logger logger = LoggerFactory.getLogger(SyncService.class);
    
    @Autowired
    private TrainingProgramRepository programRepository;
    
    @Autowired
    private UserService userService;
    
    /**
     * Get all saved programs for the current user.
     *
     * @param since Optional timestamp to get programs updated after
     * @return List of saved programs
     */
    public List<SavedProgramResponse> getSavedPrograms(OffsetDateTime since) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        
        logger.info("Getting saved programs for user: {}", username);
        
        List<SavedTrainingProgram> programs;
        if (since != null) {
            LocalDateTime sinceLocal = LocalDateTime.ofInstant(since.toInstant(), ZoneId.systemDefault());
            programs = programRepository.findByUserIdAndUpdatedAtAfter(user.getId(), sinceLocal);
        } else {
            programs = programRepository.findByUserId(user.getId());
        }
        
        return programs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Save or update a training program.
     *
     * @param request Save program request
     * @return Saved program response
     */
    public SavedProgramResponse saveProgram(SaveProgramRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        
        logger.info("Saving program for user: {}, week: {}", username, request.getWeekNumber());
        
        // Find existing program or create new
        SavedTrainingProgram program = programRepository
                .findByUserIdAndWeekNumber(user.getId(), request.getWeekNumber())
                .orElse(new SavedTrainingProgram());
        
        program.setUserId(user.getId());
        program.setWeekNumber(request.getWeekNumber());
        program.setMaxWeight(request.getMaxWeight());
        
        // Map days
        List<SavedTrainingProgram.SavedDay> days = request.getDays().stream()
                .map(this::mapToSavedDay)
                .collect(Collectors.toList());
        program.setDays(days);
        
        // Handle version for conflict resolution
        if (request.getVersion() != null) {
            program.setVersion(request.getVersion() + 1);
        } else {
            program.setVersion(1L);
        }
        
        SavedTrainingProgram saved = programRepository.save(program);
        
        logger.info("Program saved successfully for user: {}, week: {}", username, request.getWeekNumber());
        
        return mapToResponse(saved);
    }
    
    /**
     * Delete a saved program.
     *
     * @param weekNumber Week number
     */
    public void deleteProgram(Integer weekNumber) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        
        logger.info("Deleting program for user: {}, week: {}", username, weekNumber);
        
        SavedTrainingProgram program = programRepository
                .findByUserIdAndWeekNumber(user.getId(), weekNumber)
                .orElseThrow(() -> new IllegalArgumentException("Program not found"));
        
        programRepository.delete(program);
        
        logger.info("Program deleted successfully for user: {}, week: {}", username, weekNumber);
    }
    
    /**
     * Map SavedTrainingProgram to SavedProgramResponse.
     */
    private SavedProgramResponse mapToResponse(SavedTrainingProgram program) {
        SavedProgramResponse response = new SavedProgramResponse();
        response.setId(program.getId());
        response.setWeekNumber(program.getWeekNumber());
        response.setMaxWeight(program.getMaxWeight());
        
        List<SavedDayResponse> days = program.getDays().stream()
                .map(this::mapDayToResponse)
                .collect(Collectors.toList());
        response.setDays(days);
        
        if (program.getCreatedAt() != null) {
            response.setCreatedAt(OffsetDateTime.ofInstant(
                    program.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    ZoneId.systemDefault()
            ));
        }
        
        if (program.getUpdatedAt() != null) {
            response.setUpdatedAt(OffsetDateTime.ofInstant(
                    program.getUpdatedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    ZoneId.systemDefault()
            ));
        }
        
        response.setVersion(program.getVersion());
        
        return response;
    }
    
    /**
     * Map SavedDay to SavedDayResponse.
     */
    private SavedDayResponse mapDayToResponse(SavedTrainingProgram.SavedDay day) {
        SavedDayResponse response = new SavedDayResponse();
        response.setDayNumber(day.getDayNumber());
        response.setCompleted(day.getCompleted());
        
        if (day.getCompletedAt() != null) {
            response.setCompletedAt(OffsetDateTime.ofInstant(
                    day.getCompletedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    ZoneId.systemDefault()
            ));
        }
        
        List<SavedExerciseResponse> exercises = day.getExercises().stream()
                .map(this::mapExerciseToResponse)
                .collect(Collectors.toList());
        response.setExercises(exercises);
        
        return response;
    }
    
    /**
     * Map SavedExercise to SavedExerciseResponse.
     */
    private SavedExerciseResponse mapExerciseToResponse(SavedTrainingProgram.SavedExercise exercise) {
        SavedExerciseResponse response = new SavedExerciseResponse();
        response.setSetNumber(exercise.getSetNumber());
        response.setWeight(exercise.getWeight());
        response.setRepetitions(exercise.getRepetitions());
        response.setPercentage(exercise.getPercentage());
        response.setCompleted(exercise.getCompleted());
        response.setActualRepetitions(exercise.getActualRepetitions());
        
        return response;
    }
    
    /**
     * Map SaveDayRequest to SavedDay.
     */
    private SavedTrainingProgram.SavedDay mapToSavedDay(SaveDayRequest request) {
        SavedTrainingProgram.SavedDay day = new SavedTrainingProgram.SavedDay();
        day.setDayNumber(request.getDayNumber());
        day.setCompleted(request.getCompleted());
        
        if (request.getCompletedAt() != null) {
            day.setCompletedAt(LocalDateTime.ofInstant(
                    request.getCompletedAt().toInstant(),
                    ZoneId.systemDefault()
            ));
        }
        
        List<SavedTrainingProgram.SavedExercise> exercises = request.getExercises().stream()
                .map(this::mapToSavedExercise)
                .collect(Collectors.toList());
        day.setExercises(exercises);
        
        return day;
    }
    
    /**
     * Map SaveExerciseRequest to SavedExercise.
     */
    private SavedTrainingProgram.SavedExercise mapToSavedExercise(SaveExerciseRequest request) {
        SavedTrainingProgram.SavedExercise exercise = new SavedTrainingProgram.SavedExercise();
        exercise.setSetNumber(request.getSetNumber());
        exercise.setWeight(request.getWeight());
        exercise.setRepetitions(request.getRepetitions());
        exercise.setPercentage(request.getPercentage());
        exercise.setCompleted(request.getCompleted());
        exercise.setActualRepetitions(request.getActualRepetitions());
        
        return exercise;
    }
}