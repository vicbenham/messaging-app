package org.example.messagingapp.repositories;

import org.example.messagingapp.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    <T>Collection<T> findAllByChatIdOrderBySentAt(Long chatId, Class<T> type);
}
