package com.example.chat_relex.service;


import com.corundumstudio.socketio.SocketIOClient;
import com.example.chat_relex.mapper.MessageMapper;
import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.models.Request.MessageRequest;
import com.example.chat_relex.models.Request.MessageRequestWithoutSender;
import com.example.chat_relex.models.Request.UserRequest;
import com.example.chat_relex.models.Response.MessageResponse;
import com.example.chat_relex.models.entity.ChatRoom;
import com.example.chat_relex.models.entity.Message;
import com.example.chat_relex.models.entity.User;
import com.example.chat_relex.repository.ChatRoomRepository;
import com.example.chat_relex.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocketService {
    private final ChatRoomRepository chatRoomRepository;

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final UserService userService;

    private final UserMapper userMapper;

    @Transactional
    public void sendMessage(String chatRoomId, String eventName, SocketIOClient senderClient, String message, String senderNickName) {
        for (
                SocketIOClient client : senderClient.getNamespace().getRoomOperations(chatRoomId).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId)).get();
                User sender = userMapper.toEntityFromDTO(userService.getUserByNickName(senderNickName));
                System.out.println(sender.getNickname());
                MessageResponse messageResponse = new MessageResponse(message, sender, chatRoom);

                messageRepository.save(messageMapper.toEntityFromResponse(messageResponse));
                UserRequest userRequest = userMapper.toRequestFromEntity(sender);
                client.sendEvent(eventName, new MessageRequest(message, userRequest));
            }
        }
    }

    @Transactional
    public List<Message> getMessageHistory(String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId)).get();
        List<Message> messages = messageRepository.getByChatRoom(chatRoom);
        return messages;
    }

    public void sendMessageHistory(String chatRoomId, String eventName, SocketIOClient senderClient, String message, String senderNickName) {
        for (
                SocketIOClient client : senderClient.getNamespace().getRoomOperations(chatRoomId).getClients()) {
            User sender = userMapper.toEntityFromDTO(userService.getUserByNickName(senderNickName));
            long idSender=chatRoomRepository.findByChatRoomIdAndUserSenderId(Long.valueOf(chatRoomId), sender.getUserId());
            System.out.println(idSender);
            User recipient = userMapper.toEntityFromDTO(userService.getUserById(idSender));
            UserRequest userRequest = userMapper.toRequestFromEntity(recipient);
            if (eventName.equals("get_message")) {
                client.sendEvent(eventName, new MessageRequest(message, userRequest));
            } else if (eventName.equals("send_message")) {
                client.sendEvent(eventName, new MessageRequestWithoutSender(message));
            }
        }

    }
}