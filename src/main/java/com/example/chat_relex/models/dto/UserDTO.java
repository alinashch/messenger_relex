package com.example.chat_relex.models.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserDTO implements UserDetails {

    @JsonIgnore
    private Long userId;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String  login;
    @JsonIgnore
    private String passwordHash;
    private String email;

    private String nickname;
    private Boolean isVerified;

    private Boolean isActive ;

    private Boolean isShowFriends;

    private Boolean isCanReceiveMessageFromNotFriend;

    private Set<RoleDTO> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isVerified;
    }
}