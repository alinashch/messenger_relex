package com.example.chat_relex.models.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    private Boolean isVerified;


}
