package com.example.chat_relex.models.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfilePassword {

    @NotBlank(message = "Пароль не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 20, message = "Пароль не может быть меньше 8 и больше 20 символов")
    private String password;

    @NotBlank(message = "Пароль не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 20, message = "Пароль не может быть меньше 8 и больше 20 символов")
    private String repeatPassword;
}
