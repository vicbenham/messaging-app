package org.example.messagingapp.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {

    // Permet de préciser un message d'erreur quand on gère les messages coté back
    String message() default "File size is too big";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
