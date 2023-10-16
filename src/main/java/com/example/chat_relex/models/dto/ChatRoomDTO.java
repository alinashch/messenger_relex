package com.example.chat_relex.models.dto;

import com.example.chat_relex.models.entity.User;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ChatRoomDTO {

    private Long chatRoomId;

    private Set<User> users;

}
