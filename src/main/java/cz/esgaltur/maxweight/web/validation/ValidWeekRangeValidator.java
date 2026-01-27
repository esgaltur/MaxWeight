package cz.esgaltur.maxweight.web.validation;

import cz.esgaltur.maxweight.core.model.Week;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidWeekRangeValidator implements ConstraintValidator<ValidWeekRange, WeekRange> {

    @Override
    public boolean isValid(WeekRange value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        int fromWeek = value.getFromWeek();
        int toWeek = value.getToWeek();

        if (!Week.isValidWeekNumber(fromWeek) || !Week.isValidWeekNumber(toWeek)) {
            return true;
        }

        if (fromWeek <= toWeek) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
            "Invalid week range. 'fromWeek' must be less than or equal to 'toWeek'."
        ).addConstraintViolation();
        return false;
    }
}
