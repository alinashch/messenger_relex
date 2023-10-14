package com.example.chat_relex.models.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserRequest {

    private String nickname;

    private String firstName;

    private String lastName;

}
