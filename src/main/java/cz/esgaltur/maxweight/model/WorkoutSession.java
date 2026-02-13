package cz.esgaltur.maxweight.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Workout session document for tracking completed workouts.
 */
@Document(collection = "workout_sessions")
public class WorkoutSession {
    
    @Id
    private String id;
    
    private String userId;
    
    private Integer weekNumber;
    
    private Integer dayNumber;
    
    private Double maxWeight;
    
    private List<CompletedExercise> exercises;
    
    @CreatedDate
    private LocalDateTime completedAt;
    
    private Integer totalSets;
    
    private Integer totalReps;
    
    private Double totalVolume; // weight * reps
    
    private Long duration; // in seconds
    
    private String notes;
    
    // Nested class for completed exercise
    public static class CompletedExercise {
        private Integer setNumber;
        private Double plannedWeight;
        private Integer plannedReps;
        private Double actualWeight;
        private Integer actualReps;
        private Boolean completed;
        
        public CompletedExercise() {
        }
        
        public Integer getSetNumber() {
            return setNumber;
        }
        
        public void setSetNumber(Integer setNumber) {
            this.setNumber = setNumber;
        }
        
        public Double getPlannedWeight() {
            return plannedWeight;
        }
        
        public void setPlannedWeight(Double plannedWeight) {
            this.plannedWeight = plannedWeight;
        }
        
        public Integer getPlannedReps() {
            return plannedReps;
        }
        
        public void setPlannedReps(Integer plannedReps) {
            this.plannedReps = plannedReps;
        }
        
        public Double getActualWeight() {
            return actualWeight;
        }
        
        public void setActualWeight(Double actualWeight) {
            this.actualWeight = actualWeight;
        }
        
        public Integer getActualReps() {
            return actualReps;
        }
        
        public void setActualReps(Integer actualReps) {
            this.actualReps = actualReps;
        }
        
        public Boolean getCompleted() {
            return completed;
        }
        
        public void setCompleted(Boolean completed) {
            this.completed = completed;
        }
    }
    
    // Constructors
    public WorkoutSession() {
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Integer getWeekNumber() {
        return weekNumber;
    }
    
    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }
    
    public Integer getDayNumber() {
        return dayNumber;
    }
    
    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }
    
    public Double getMaxWeight() {
        return maxWeight;
    }
    
    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }
    
    public List<CompletedExercise> getExercises() {
        return exercises;
    }
    
    public void setExercises(List<CompletedExercise> exercises) {
        this.exercises = exercises;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public Integer getTotalSets() {
        return totalSets;
    }
    
    public void setTotalSets(Integer totalSets) {
        this.totalSets = totalSets;
    }
    
    public Integer getTotalReps() {
        return totalReps;
    }
    
    public void setTotalReps(Integer totalReps) {
        this.totalReps = totalReps;
    }
    
    public Double getTotalVolume() {
        return totalVolume;
    }
    
    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }
    
    public Long getDuration() {
        return duration;
    }
    
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutSession that = (WorkoutSession) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}