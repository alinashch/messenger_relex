package com.example.chat_relex.repository;


import com.example.chat_relex.models.entity.ChatRoom;
import com.example.chat_relex.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>  {

    ChatRoom save(ChatRoom chatRoom);


}
