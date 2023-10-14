package com.example.chat_relex.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokensDTO {

    private String accessToken;

    private String refreshToken;
}