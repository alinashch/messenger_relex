package com.example.chat_relex.models.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ChatRoomDTO {
    private Long charRoomId;

    private UUID token;

    private int usersCount;
}
