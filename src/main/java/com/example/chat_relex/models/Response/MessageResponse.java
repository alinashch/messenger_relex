package com.example.chat_relex.models.Response;


import com.example.chat_relex.models.entity.ChatRoom;
import com.example.chat_relex.models.entity.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class MessageResponse {

    private String message;

    private User userSender;

    private ChatRoom chatRoom;

}
