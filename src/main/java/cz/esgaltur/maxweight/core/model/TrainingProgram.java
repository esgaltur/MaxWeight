package cz.esgaltur.maxweight.core.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a training program for a specific week.
 * Encapsulates the list of exercises for each day of the week.
 */
public class TrainingProgram {
    private final Week week;
    private final int maxWeight;
    private final Map<Day, List<Exercise>> exercises;

    /**
     * Constructor for creating a training program
     * 
     * @param week The week this program is for
     * @param maxWeight The maximum weight for bench press
     */
    public TrainingProgram(Week week, int maxWeight) {
        this.week = week;
        this.maxWeight = maxWeight;
        this.exercises = new EnumMap<>(Day.class);

        // Initialize empty lists for each day
        for (Day day : Day.values()) {
            exercises.put(day, new ArrayList<>());
        }
    }

    /**
     * Get the week this program is for
     * 
     * @return The week
     */
    public Week getWeek() {
        return week;
    }

    /**
     * Get the maximum weight for bench press
     * 
     * @return The maximum weight
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     * Get the exercises for a specific day
     * 
     * @param day The day to get exercises for
     * @return The list of exercises for the day
     */
    public List<Exercise> getExercisesForDay(Day day) {
        return exercises.get(day);
    }

    /**
     * Add an exercise to a specific day
     * 
     * @param exercise The exercise to add
     */
    public void addExercise(Exercise exercise) {
        exercises.get(exercise.getDay()).add(exercise);
    }

    /**
     * Check if this program is for weeks 5 or 6
     * 
     * @return true if this program is for weeks 5 or 6, false otherwise
     */
    public boolean isSpecialWeek() {
        return week == Week.WEEK5 || week == Week.WEEK6;
    }

    /**
     * Get the exercises map
     * 
     * @return The map of exercises for each day
     */
    public Map<Day, List<Exercise>> getExercises() {
        return exercises;
    }
}
