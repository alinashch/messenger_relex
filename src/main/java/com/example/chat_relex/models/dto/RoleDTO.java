package com.example.chat_relex.models.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Data
public class RoleDTO implements GrantedAuthority {

    private Long roleId;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}