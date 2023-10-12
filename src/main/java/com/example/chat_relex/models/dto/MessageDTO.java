package com.example.chat_relex.models.dto;

import com.example.chat_relex.models.entity.ChatRoom;
import com.example.chat_relex.models.entity.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {

    private Long messageId;

    private String message;

    private String sender;

    private String room;
}
