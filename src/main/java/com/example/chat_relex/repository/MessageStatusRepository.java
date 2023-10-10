package com.example.chat_relex.repository;

import com.example.chat_relex.models.entity.MessageStatus;
import com.example.chat_relex.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface MessageStatusRepository extends JpaRepository<MessageStatus, Long> {

    Optional<MessageStatus> findByMessageStatus(String messageStatus);


}