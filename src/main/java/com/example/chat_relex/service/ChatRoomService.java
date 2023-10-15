package com.example.chat_relex.service;


import com.example.chat_relex.exceptions.EmailNotVerification;
import com.example.chat_relex.exceptions.NotActiveUserException;
import com.example.chat_relex.exceptions.NotOnFriendsListException;
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
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private  final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ChatRoomMapper chatRoomMapper;

    private final FriendService friendService;

    @Transactional
    public ChatroomResponse startNewChat(StartChatForm startChatForm, UserDTO sender){
        if(!sender.getIsActive() || !userRepository.getByNickname(startChatForm.getRecipientNickname()).get().getIsActive()){
            throw new NotActiveUserException("The user is not active");
        }
        if(!sender.getIsVerified() || !userRepository.getByNickname(startChatForm.getRecipientNickname()).get().getIsVerified()){
            throw new EmailNotVerification("The email is not verification ");
        }
        if(!userRepository.getByNickname(startChatForm.getRecipientNickname()).get().getIsCanReceiveMessageFromNotFriend()
                & !friendService.hasFriend(userRepository.getByNickname(startChatForm.getRecipientNickname()).get(), userMapper.toEntityFromDTO(sender))){
            throw new NotOnFriendsListException("Not on the friends list");
        }
        List<Long> list=chatRoomRepository.findByUserId(sender.getUserId());
        long id=0;
        for(Long l: list){
            System.out.println(l);
             id=chatRoomRepository.findChatRoomByChatRoomIdAndUserSenderId(l, sender.getUserId());
        }
        if(id!=0){
            return new ChatroomResponse(id);
        }
        Set<User> users=new HashSet<>();
        users.add(userRepository.getByNickname(startChatForm.getRecipientNickname()).get());
        users.add(userMapper.toEntityFromDTO(sender));
        ChatRoom chatRoom=chatRoomMapper.toEntityFromRequest(new ChatRoomRequest(users));
        chatRoomRepository.save(chatRoom);
        return chatRoomMapper.toResponseFromEntity(chatRoom);
    }

}
