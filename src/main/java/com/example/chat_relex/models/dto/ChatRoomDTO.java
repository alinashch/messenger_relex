package com.example.chat_relex.models.dto;

import com.example.chat_relex.models.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ChatRoomDTO {

    private Long chatRoomId;

    private Set<User> users;

}
