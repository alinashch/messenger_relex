package com.example.chat_relex.models.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "Токен не должен быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String refreshToken;
}