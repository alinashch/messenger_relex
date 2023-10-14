package com.example.chat_relex.repository;


import com.example.chat_relex.models.entity.ChatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom save(ChatRoom chatRoom);

    @Query(value = """
            SELECT b.user_id FROM user_chat_room b WHERE  b.chat_room_id =:chatRoomId AND b.user_id <> :userSenderId """, nativeQuery = true)
    long findByChatRoomIdAndUserSenderId(@Param("chatRoomId") Long chatRoomId,
                                         @Param("userSenderId") Long userSenderId);

}
