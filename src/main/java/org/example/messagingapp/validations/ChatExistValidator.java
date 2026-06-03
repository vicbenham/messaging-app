package org.example.messagingapp.validations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.messagingapp.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatExistValidator implements ConstraintValidator<ChatExist, Long> {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public boolean isValid(Long chatId, ConstraintValidatorContext constraintValidatorContext) {
        return chatRepository.existsById(chatId);
    }
}
