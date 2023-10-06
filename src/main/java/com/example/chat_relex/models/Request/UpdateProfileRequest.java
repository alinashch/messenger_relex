package com.example.chat_relex.models.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

    @NotBlank(message = "Указанное имя не должно быть пустым")
    @Size(max = 100, message = "Имя не может быть больше 100 символов")
    private String firstName;

    @NotBlank(message = "Указанная фамилия не должно быть пустым")
    @Size(max = 100, message = "Фамилия не может быть больше 100 символов")
    private String lastName;

    @NotBlank(message = "Указанный никнейм не должно быть пустым")
    @Size(max = 100, message = "Никнейм не может быть больше 100 символов")
    private String nickname;
}