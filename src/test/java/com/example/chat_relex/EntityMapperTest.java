package com.example.chat_relex;

import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.mapper.UserMapperImpl;
import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.models.Response.UserResponse;
import com.example.chat_relex.models.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EntityMapperTest {
 private UserMapper userMapper=new UserMapperImpl();


 @Test
 void contextLoads() {
  SignUpForm signUpForm=new SignUpForm("qqq", "dcdc","12345678", "csscsc@mail.ru", "scscs", "scsc", "12345678" );
 UserEntity user= userMapper.fromSignUpFormToEntity(signUpForm);
  assertThat(user).hasFieldOrPropertyWithValue("email", "csscsc@mail.ru");
 }
 @Test
 void contextgetAll() {
  List<UserEntity> set=new ArrayList<>();
  set.add(new UserEntity(1L, "scsc","scc", "cscs", "scsc", "cscs", "csscs"));
  List<UserResponse>  user= userMapper.fromEntityToResponseList(set);
  assertThat(user.get(0)).hasFieldOrPropertyWithValue("email", "cscs");
  System.out.println(user.get(0).getName());
 }
}
