package com.example.chat_relex.confirurations;

import com.example.chat_relex.mapper.MessageStatusMapper;
import com.example.chat_relex.mapper.RoleMapper;
import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.mapper.VerificationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfiguration {

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

    @Bean
    public RoleMapper roleMapper() {
        return RoleMapper.INSTANCE;
    }

    @Bean
    public VerificationMapper verificationMapper() {
        return VerificationMapper.INSTANCE;
    }

    @Bean
    public MessageStatusMapper messageStatusMapper() {
        return MessageStatusMapper.INSTANCE;
    }

}
