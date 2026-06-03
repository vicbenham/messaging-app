package org.example.messagingapp.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements
        ConstraintValidator<FileSize, MultipartFile> {

    private final long MAXSIZE = (long) 2e+7;

    @Override
    public boolean isValid(MultipartFile value,
                           ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return (value.getSize() < MAXSIZE);
    }

}
