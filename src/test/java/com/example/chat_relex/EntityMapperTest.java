package com.example.chat_relex;

import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.mapper.UserMapperImpl;
import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.models.Response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EntityMapperTest {
 private UserMapper userMapper=new UserMapperImpl();


}
