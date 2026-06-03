package org.example.messagingapp.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = ChatExistValidator.class)
public @interface ChatExist {
    String message() default "Chat not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
