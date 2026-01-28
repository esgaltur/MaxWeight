package cz.esgaltur.maxweight.web.validation;

import cz.esgaltur.maxweight.core.model.Week;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidWeekValidator implements ConstraintValidator<ValidWeek, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (Week.isValidWeekNumber(value)) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
            String.format(
                "Invalid week number. Please select a week between %d and %d.",
                Week.minWeekNumber(),
                Week.maxWeekNumber()
            )
        ).addConstraintViolation();
        return false;
    }
}
