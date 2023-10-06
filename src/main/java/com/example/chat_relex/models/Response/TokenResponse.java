package com.example.chat_relex.models.Response;
import lombok.*;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
