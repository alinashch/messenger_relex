package com.example.chat_relex.repository;

import com.example.chat_relex.models.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message save(Message message);
}
