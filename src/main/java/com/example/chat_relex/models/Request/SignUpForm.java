package com.example.chat_relex.models.Request;


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

    @NotBlank
    @Size(max = 100)
    private String nickname;

    @NotBlank
    @Size(max = 100)
    private String login;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    @NotBlank
    @Size(max = 255)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "/^\\p{L}+$/i")
    private String name;

    @NotBlank
    @Size(max = 100)
    private String surname;


    @NotBlank
    @Size(min = 8, max = 50)
    private String repeatPassword;
}
