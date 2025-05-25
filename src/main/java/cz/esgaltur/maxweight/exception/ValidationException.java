package cz.esgaltur.maxweight.exception;

/**
 * Exception thrown when input validation fails.
 * Provides more specific error messages for different types of validation errors.
 */
public class ValidationException extends RuntimeException {
    
    /**
     * Constructs a new validation exception with the specified detail message.
     * 
     * @param message The detail message
     */
    public ValidationException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new validation exception with the specified detail message and cause.
     * 
     * @param message The detail message
     * @param cause The cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Factory method for creating a week range validation exception.
     * 
     * @param from The starting week number
     * @param to The ending week number
     * @return A new ValidationException with a message about the invalid week range
     */
    public static ValidationException invalidWeekRange(int from, int to) {
        return new ValidationException(
            String.format("Invalid week range: from=%d, to=%d. Week numbers must be between 1 and 6, and 'from' must be less than or equal to 'to'.", from, to)
        );
    }
    
    /**
     * Factory method for creating a max weight validation exception.
     * 
     * @param maxWeight The maximum weight
     * @return A new ValidationException with a message about the invalid max weight
     */
    public static ValidationException invalidMaxWeight(int maxWeight) {
        return new ValidationException(
            String.format("Invalid max weight: %d. Max weight must be greater than 0.", maxWeight)
        );
    }
    
    /**
     * Factory method for creating a number format validation exception.
     * 
     * @param input The input string
     * @param fieldName The name of the field being validated
     * @return A new ValidationException with a message about the invalid number format
     */
    public static ValidationException invalidNumberFormat(String input, String fieldName) {
        return new ValidationException(
            String.format("Invalid number format for %s: '%s'. Please enter a valid number.", fieldName, input)
        );
    }
}