package org.example.messagingapp.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.messagingapp.repositories.ChatRepository;
import org.springframework.stereotype.Component;

@Component
public class ChatExistValidator implements ConstraintValidator<ChatExist, Long> {

    private final ChatRepository chatRepository;

    ChatExistValidator(ChatRepository chatRepository){
        this.chatRepository = chatRepository;
    }
    @Override
    public boolean isValid(Long chatId, ConstraintValidatorContext constraintValidatorContext) {
        return chatRepository.existsById(chatId);
    }
}
