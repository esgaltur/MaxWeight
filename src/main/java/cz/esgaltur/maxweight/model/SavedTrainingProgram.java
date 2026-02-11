package cz.esgaltur.maxweight.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Saved training program document for MongoDB persistence.
 * Used for syncing training programs with mobile clients.
 */
@Document(collection = "training_programs")
public class SavedTrainingProgram {
    
    @Id
    private String id;
    
    private String userId;
    
    private Integer weekNumber;
    
    private Double maxWeight;
    
    private List<SavedDay> days;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    private Long version;
    
    // Nested class for Day
    public static class SavedDay {
        private Integer dayNumber;
        private List<SavedExercise> exercises;
        private Boolean completed;
        private LocalDateTime completedAt;
        
        public SavedDay() {
        }
        
        public Integer getDayNumber() {
            return dayNumber;
        }
        
        public void setDayNumber(Integer dayNumber) {
            this.dayNumber = dayNumber;
        }
        
        public List<SavedExercise> getExercises() {
            return exercises;
        }
        
        public void setExercises(List<SavedExercise> exercises) {
            this.exercises = exercises;
        }
        
        public Boolean getCompleted() {
            return completed;
        }
        
        public void setCompleted(Boolean completed) {
            this.completed = completed;
        }
        
        public LocalDateTime getCompletedAt() {
            return completedAt;
        }
        
        public void setCompletedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
        }
    }
    
    // Nested class for Exercise
    public static class SavedExercise {
        private Integer setNumber;
        private Double weight;
        private Integer repetitions;
        private Integer percentage;
        private Boolean completed;
        private Integer actualRepetitions;
        
        public SavedExercise() {
        }
        
        public Integer getSetNumber() {
            return setNumber;
        }
        
        public void setSetNumber(Integer setNumber) {
            this.setNumber = setNumber;
        }
        
        public Double getWeight() {
            return weight;
        }
        
        public void setWeight(Double weight) {
            this.weight = weight;
        }
        
        public Integer getRepetitions() {
            return repetitions;
        }
        
        public void setRepetitions(Integer repetitions) {
            this.repetitions = repetitions;
        }
        
        public Integer getPercentage() {
            return percentage;
        }
        
        public void setPercentage(Integer percentage) {
            this.percentage = percentage;
        }
        
        public Boolean getCompleted() {
            return completed;
        }
        
        public void setCompleted(Boolean completed) {
            this.completed = completed;
        }
        
        public Integer getActualRepetitions() {
            return actualRepetitions;
        }
        
        public void setActualRepetitions(Integer actualRepetitions) {
            this.actualRepetitions = actualRepetitions;
        }
    }
    
    // Constructors
    public SavedTrainingProgram() {
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
    
    public Double getMaxWeight() {
        return maxWeight;
    }
    
    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }
    
    public List<SavedDay> getDays() {
        return days;
    }
    
    public void setDays(List<SavedDay> days) {
        this.days = days;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Long getVersion() {
        return version;
    }
    
    public void setVersion(Long version) {
        this.version = version;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedTrainingProgram that = (SavedTrainingProgram) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}