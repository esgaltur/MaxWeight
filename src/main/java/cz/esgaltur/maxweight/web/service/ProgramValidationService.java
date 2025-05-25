package cz.esgaltur.maxweight.web.service;

import org.springframework.stereotype.Service;

/**
 * Service for validating program generation inputs.
 * Extracts common validation logic used across controllers.
 */
@Service
public class ProgramValidationService {

    /**
     * Validates a single week number
     * 
     * @param weekNumber The week number to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidWeek(int weekNumber) {
        return weekNumber >= 1 && weekNumber <= 6;
    }

    /**
     * Validates a week range
     * 
     * @param fromWeek The starting week number
     * @param toWeek The ending week number
     * @return true if valid, false otherwise
     */
    public boolean isValidWeekRange(int fromWeek, int toWeek) {
        return isValidWeek(fromWeek) && isValidWeek(toWeek) && fromWeek <= toWeek;
    }

    /**
     * Validates the maximum weight
     * 
     * @param maxWeight The maximum weight to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidMaxWeight(int maxWeight) {
        return maxWeight > 0;
    }

    /**
     * Gets the error message for an invalid week number
     * 
     * @return The error message
     */
    public String getInvalidWeekMessage() {
        return "Invalid week number. Please select a week between 1 and 6.";
    }

    /**
     * Gets the error message for an invalid week range
     * 
     * @return The error message
     */
    public String getInvalidWeekRangeMessage() {
        return "Invalid week range. Please select weeks between 1 and 6, with 'from' week less than or equal to 'to' week.";
    }

    /**
     * Gets the error message for an invalid maximum weight
     * 
     * @return The error message
     */
    public String getInvalidMaxWeightMessage() {
        return "Max weight must be greater than 0.";
    }
}