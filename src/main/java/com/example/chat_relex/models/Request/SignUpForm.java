package com.example.chat_relex.models.Request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpForm {

    @NotBlank(message = "Никнейм не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @NotBlank(message = "Логин не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 20, message = "Логин не может быть меньше 8 и больше 20 символов")
    private String login;

    @NotBlank(message = "Пароль не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 20, message = "Пароль не может быть меньше 8 и больше 20 символов")
    private String password;

    @Email(message = "Почта указана неверно")
    @NotBlank(message = "Почта не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Имя не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Имя не может быть больше 100 символов")
    private String firstname;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Фамилия не может быть больше 100 символов")
    private String lastName;


    @NotBlank(message = "Пароль не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 20, message = "Пароль не может быть меньше 8 и больше 20 символов")
    private String repeatPassword;
}
