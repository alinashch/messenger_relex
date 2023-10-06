package com.example.chat_relex.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CredentialsDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String nickname;
    private Boolean isVerified;
    private String login;
    private Set<RoleDTO> roles;
}