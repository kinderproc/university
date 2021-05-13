package com.foxminded.university.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.foxminded.university.validation.validator.GroupCapacityValidator;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GroupCapacityValidator.class)
public @interface GroupCapacityConstraint {
    String message() default "Group is full!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String template();
}
