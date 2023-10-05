package com.example.chat_relex.mapper;


import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.models.Response.UserResponse;
import com.example.chat_relex.models.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse fromUserEntityToUserResponse(UserEntity user);

    UserEntity fromUserResponseToUserEntity(UserResponse user);

    @Mapping(source = "signUpForm.nickname", target = "nickname")
    @Mapping(source = "signUpForm.surname", target = "surname")
    @Mapping(source = "signUpForm.name", target = "name")
    @Mapping(source = "signUpForm.password", target = "password")
    @Mapping(source = "signUpForm.email", target = "email")
    @Mapping(source = "signUpForm.login", target = "login")
    UserEntity fromSignUpFormToEntity(SignUpForm signUpForm);

    @Mapping(source = "signUpForm.nickname", target = "nickname")
    @Mapping(source = "signUpForm.surname", target = "surname")
    @Mapping(source = "signUpForm.name", target = "name")
    @Mapping(source = "hashPassword", target = "password")
    @Mapping(source = "signUpForm.email", target = "email")
    @Mapping(source = "signUpForm.login", target = "login")
    UserEntity fromSignUpFormToEntity(SignUpForm signUpForm, String hashPassword);


    List<UserResponse> fromEntityToResponseList(List<UserEntity> set);
}

