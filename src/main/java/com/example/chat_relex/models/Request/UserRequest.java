package com.example.chat_relex.models.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private String nickname;

    private String firstName;

    private String lastName;

}
