package com.example.chat_relex.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendDTO {

    private Long userId;

    private String email;

    private String nickname;
}