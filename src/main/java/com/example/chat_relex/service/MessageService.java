package com.example.chat_relex.service;

import com.example.chat_relex.exceptions.MessageNotFoundException;
import com.example.chat_relex.mapper.MessageStatusMapper;
import com.example.chat_relex.models.entity.Message;
import com.example.chat_relex.models.entity.MessageStatus;
import com.example.chat_relex.repository.MessageRepository;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.chat_relex.models.constant.MessageStatus.RECEIVED;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    private MessageStatusService messageStatusService;

    private ChatRoomService chatRoomService;

    private  MessageStatusMapper messageStatusMapper;


    public Message save(Message chatMessage) {
        chatMessage.setStatus(messageStatusMapper
                .toEntityFromDTO(messageStatusService.getMessageStatusReceived()));
        messageRepository.save(chatMessage);
        return chatMessage;
    }

//    public long countNewMessages(Long senderId, Long recipientId) {
//        return messageRepository.countBySenderIdAndRecipientIdAndStatus(senderId, recipientId,
//                messageStatusService.getMessageStatusID(messageStatusMapper.toEntityFromDTO(messageStatusService.getMessageStatusReceived())));
//    }

    public List<Message> findChatMessages(Long senderId, Long recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);

        var messages = chatId.map(cId -> messageRepository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, messageStatusMapper.toEntityFromDTO( messageStatusService.getMessageStatusDelivered()));
        }

        return messages;
    }

    public Message findById(Long id) {
        return messageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(messageStatusMapper.toEntityFromDTO( messageStatusService.getMessageStatusDelivered()));
                    return messageRepository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new MessageNotFoundException("can't find message (" + id + ")"));
    }
    public void updateStatuses(Long senderId, Long recipientId, MessageStatus status) {
    }

}
