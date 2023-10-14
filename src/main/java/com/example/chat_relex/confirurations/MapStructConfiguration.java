package com.example.chat_relex.confirurations;

import com.example.chat_relex.mapper.*;
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
    public MessageMapper messageMapper() {
        return MessageMapper.INSTANCE;
    }

    @Bean
    public ChatRoomMapper chatRoomMapper() {
        return ChatRoomMapper.INSTANCE;}

    @Bean
    public VerificationMapper verificationMapper() {
        return VerificationMapper.INSTANCE;
    }

}
