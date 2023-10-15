package com.example.chat_relex.service;

import com.example.chat_relex.exceptions.*;
import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.models.Request.UpdateProfilePassword;
import com.example.chat_relex.models.Request.UpdateProfileRequest;
import com.example.chat_relex.models.dto.CredentialsDTO;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.models.entity.User;
import com.example.chat_relex.repository.RefreshUserTokenRepository;
import com.example.chat_relex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    private final RefreshUserTokenRepository refreshTokenRepository;


    public UserDTO getUserByEmail(String email) {
        User user = userRepository.getByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь с данной почтой не существует")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь с данным ИД не существует")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserByNickName(String nickname) {

        User user = userRepository.getByNickname(nickname).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь с данным ником не существует")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserByLogin(String login) {

        User user = userRepository.getByLogin(login).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь с данным логином не существует")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserByEmailWithVerificationCheck(String email) {
        UserDTO user = getUserByEmail(email);
        if (!user.getIsVerified()) {
            throw new UserNotVerifiedException("Пользователь не верифицирован");
        }
        return user;
    }

    @Transactional
    public UserDTO registerUser(SignUpForm request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityAlreadyExistsException("Пользователь с данной почтой уже существует");
        }
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new EntityAlreadyExistsException("Пользователь с данным логином уже существует");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new EntityAlreadyExistsException("Пользователь с данным ником уже существует");
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Пароли не совпадают");
        }
        User user = userMapper.toEntityFromRequest(request, Set.of(roleService.getUserRole()), BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        user = userRepository.save(user);
        return userMapper.toDTOFromEntity(user);
    }
    @Transactional
    public void verifyUserById(Long id) {
        userRepository.verifyUserById(id);
    }
    @Transactional
    public CredentialsDTO getCredentials(UserDTO user) {
        if(refreshTokenRepository.getAllByUser_UserId(user.getUserId())==0){
            throw new TokenExpiredException("The token is not valid\n ");
        }
        if(!user.getIsVerified()){
            throw new TokenExpiredException("Email not verification ");
        }
        return userMapper.toCredentialsDTOFromDTO(user);
    }

    @Transactional
    public void updateProfile(UserDTO user, UpdateProfileRequest request) {
        if(refreshTokenRepository.getAllByUser_UserId(user.getUserId())==0){
            throw new TokenExpiredException("The token is not valid\n");
        }
        if (userRepository.existsByLogin(request.getNickname())) {
            throw new EntityAlreadyExistsException("Пользователь с данным никнеймом уже существует");
        }
        if(!user.getIsVerified()){
            throw new TokenExpiredException("Email not verification ");
        }
        User entity = userMapper.toEntityFromDTO(user);
        userMapper.updateEntity(request, entity);
        userRepository.save(entity);
    }

    @Transactional
    public void updatePassword(UserDTO user , UpdateProfilePassword request) {
        if(refreshTokenRepository.getAllByUser_UserId(user.getUserId())==0){
            throw new TokenExpiredException("The token is not valid\n");
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Пароли не совпадают");
        }
        if(!user.getIsVerified()){
            throw new TokenExpiredException("Email not verification ");
        }
        User entity = userMapper.toEntityFromDTO(user);
        entity.setPasswordHash(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        System.out.println(entity.getEmail());
        userMapper.updateEntity(request, entity);
         userRepository.save(entity);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if(refreshTokenRepository.getAllByUser_UserId(userId)==0){
            throw new TokenExpiredException("The token is not valid\n");
        }
        UserDTO user=userMapper.toDTOFromEntity(userRepository.findById(userId).get());
        if(!user.getIsVerified()){
            throw new TokenExpiredException("Email not verification ");
        }
        userRepository.deleteToken(userId);
        userRepository.delete(userRepository.findById(userId).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь с данным ИД не существует")
        ));
    }
    @Transactional
    public void deleteSession(String  login) {
        UserDTO user=userMapper.toDTOFromEntity(userRepository.getByLogin(login).get());
        if(refreshTokenRepository.getAllByUser_UserId(user.getUserId())==0){
            throw new TokenExpiredException("The token is not valid\n");
        }
        if(!user.getIsVerified()){
            throw new TokenExpiredException("Email not verification ");
        }
        userRepository.deleteToken(userRepository.getByLogin(login).get().getUserId());
    }


}