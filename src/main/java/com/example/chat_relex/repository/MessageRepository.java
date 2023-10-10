package com.example.chat_relex.repository;

import com.example.chat_relex.models.entity.Message;
import com.example.chat_relex.models.entity.MessageStatus;
import com.example.chat_relex.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface MessageRepository extends JpaRepository<Message, Long> {

   // long countBySenderIdAndRecipientIdAndStatus(Long senderId, Long recipientId, Long status);

    List<Message> findByChatId(String chatId);
    Message save(Message entity);

//    @Modifying
//    @Query(value = "UPDATE message  SET message_status_id =: message_status_id WHERE sender_id  = :senderId AND recipient_id  = :recipientId",  nativeQuery = true)
//    void update(@Param("message_status_id") Long message_status_id,
//                        @Param("senderId") Long senderId,
//                        @Param("recipientId") Long recipientId);

}
