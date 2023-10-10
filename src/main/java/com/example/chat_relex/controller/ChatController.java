package com.example.chat_relex.controller;

import com.example.chat_relex.models.entity.ChatNotification;
import com.example.chat_relex.models.entity.Message;
import com.example.chat_relex.service.ChatRoomService;
import com.example.chat_relex.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired private MessageService chatMessageService;
    @Autowired private ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message chatMessage) {
        var chatId = chatRoomService
                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
        chatMessage.setChatId(chatId.get());

        Message saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientNickname(),"/queue/messages",
                new ChatNotification(
                        saved.getMessageId(),
                        saved.getSenderId(),
                        saved.getSenderNickname()));
    }

//    @GetMapping("/messages/{senderId}/{recipientId}/count")
//    public ResponseEntity<Long> countNewMessages(
//            @PathVariable Long senderId,
//            @PathVariable Long recipientId) {
//
//        return ResponseEntity
//                .ok(chatMessageService.countNewMessages(senderId, recipientId));
//    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable Long senderId,
                                                @PathVariable Long recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable Long id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }
}
