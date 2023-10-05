package com.example.chat_relex.service;

import com.example.chat_relex.models.Request.LoginForm;
import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.models.Response.UserResponse;
import com.example.chat_relex.models.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    UserResponse save(SignUpForm user);

    void deleteById(long id);

    UserResponse getByUserId(Long id);

    UserResponse login(LoginForm loginForm);


    void updateName(Long user_id, String name);

    void updateNickname(Long user_id, String nickname);

    void updateEmail(Long user_id, String email);

    void updateSurname(Long user_id, String surname);

}
