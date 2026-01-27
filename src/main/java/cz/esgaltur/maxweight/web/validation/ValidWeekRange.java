package cz.esgaltur.maxweight.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ValidWeekRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWeekRange {
    String message() default "Invalid week range. 'fromWeek' must be less than or equal to 'toWeek'.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
