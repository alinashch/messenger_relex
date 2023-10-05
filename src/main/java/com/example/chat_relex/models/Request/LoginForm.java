package com.example.chat_relex.models.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginForm {

    @NotBlank
    @Size(max = 100)
    private String login;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
}