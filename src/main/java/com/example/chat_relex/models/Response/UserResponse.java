package com.example.chat_relex.models.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

    private Long id;

    private String nickname;

    @JsonIgnore
    private String password;

    private String login;

    private String email;

    private String name;

    private String surname;

}
