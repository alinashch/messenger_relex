package com.example.chat_relex.repository;


import com.example.chat_relex.models.entity.ChatRoom;
import com.example.chat_relex.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>  {

}
