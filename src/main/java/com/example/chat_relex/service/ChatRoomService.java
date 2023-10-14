package com.example.chat_relex.service;


import com.example.chat_relex.exceptions.EmailNotVerification;
import com.example.chat_relex.exceptions.NotActiveUser;
import com.example.chat_relex.mapper.ChatRoomMapper;
import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.models.Request.ChatRoomRequest;
import com.example.chat_relex.models.Request.StartChatForm;
import com.example.chat_relex.models.Response.ChatroomResponse;
import com.example.chat_relex.models.dto.ChatRoomDTO;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.models.entity.ChatRoom;
import com.example.chat_relex.models.entity.User;
import com.example.chat_relex.repository.ChatRoomRepository;
import com.example.chat_relex.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private  final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ChatRoomMapper chatRoomMapper;


    @Transactional
    public ChatroomResponse startNewChat(StartChatForm startChatForm, UserDTO sender){
        if(!sender.getIsActive() || !userRepository.getByNickname(startChatForm.getRecipientNickname()).get().getIsActive()){
            throw new NotActiveUser("The user is not active");
        }
        if(!sender.getIsVerified() || !userRepository.getByNickname(startChatForm.getRecipientNickname()).get().getIsVerified()){
            throw new EmailNotVerification("The email is not verification ");
        }
        Set<User> users=new HashSet<>();
        users.add(userRepository.getByNickname(startChatForm.getRecipientNickname()).get());
        users.add(userMapper.toEntityFromDTO(sender));
        ChatRoom chatRoom=chatRoomMapper.toEntityFromRequest(new ChatRoomRequest(users));
        chatRoomRepository.save(chatRoom);
        return chatRoomMapper.toResponseFromEntity(chatRoom);
    }

}
