package com.example.chat_relex.repository;

import com.example.chat_relex.models.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message save(Message message);

    @Modifying
    @Query(value = """
                    INSERT INTO message(message, sender, chat_room_id)
                    VALUES(:message, :sender, :chat_room_id)""", nativeQuery = true)
    void saveNewChatRoom(@Param("message") String message,
                             @Param("sender") String sender,
                             @Param("chat_room_id") Long chat_room_id);
}
