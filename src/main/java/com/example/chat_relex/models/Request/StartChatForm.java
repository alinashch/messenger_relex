package com.example.chat_relex.models.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StartChatForm {
    @NotBlank(message = "Никнейм не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String recipientNickname;
}
