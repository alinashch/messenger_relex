package com.example.chat_relex.confirurations;

import com.example.chat_relex.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@AllArgsConstructor
public class UserDetailsServiceConfiguration {

    private final UserService userService;
    @Bean
    public UserDetailsService userDetailsService() {
        return userService::getUserByEmail;
    }
}