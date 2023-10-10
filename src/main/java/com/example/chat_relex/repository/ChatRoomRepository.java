package com.example.chat_relex.repository;

import com.example.chat_relex.models.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>  {
    Optional<ChatRoom> findBySenderIdAndRecipientId(Long senderId, Long recipientId);

}
