package com.example.chat_relex.service;


import com.corundumstudio.socketio.SocketIOClient;
import com.example.chat_relex.mapper.MessageMapper;
import com.example.chat_relex.models.Request.MessageRequest;
import com.example.chat_relex.models.Response.MessageResponse;
import com.example.chat_relex.models.dto.MessageDTO;
import com.example.chat_relex.models.entity.Message;
import com.example.chat_relex.repository.ChatRoomRepository;
import com.example.chat_relex.repository.MessageRepository;
import com.example.chat_relex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocketService {

    private final UserRepository userRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message, String senderNickName) {
        for (
                SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                MessageRequest messageRequest=new MessageRequest(message, senderNickName, room);
                messageRepository.save(messageMapper.toEntityFromRequest(messageRequest));
                client.sendEvent(eventName, new MessageResponse( message, senderNickName, room ));
            }
        }
    }

}