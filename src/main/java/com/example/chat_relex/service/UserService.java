package com.example.chat_relex.service;

import com.example.chat_relex.exceptions.*;
import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.models.Request.UpdateEmailInfoForm;
import com.example.chat_relex.models.Request.UpdateProfilePassword;
import com.example.chat_relex.models.Request.UpdateProfileRequest;
import com.example.chat_relex.models.dto.CredentialsDTO;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.models.entity.User;
import com.example.chat_relex.repository.RefreshUserTokenRepository;
import com.example.chat_relex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public UserDTO registerUser(SignUpForm request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityAlreadyExistsException("User with this email already exists");
        }
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new EntityAlreadyExistsException("User with this login already exists");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new EntityAlreadyExistsException("User with this nickname already exists");
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Password does not match");
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
        if (!user.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        if (refreshTokenRepository.getAllByUser_UserId(user.getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid ");
        }
        if(!user.getIsActive()){
            throw new NotActiveUserException("The user is not active ");
        }
        return userMapper.toCredentialsDTOFromDTO(user);
    }

    @Transactional
    public void updateProfile(UserDTO user, UpdateProfileRequest request) {
        if (refreshTokenRepository.getAllByUser_UserId(user.getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        if (userRepository.existsByLogin(request.getNickname())) {
            throw new EntityAlreadyExistsException("User with this nickname already exists");
        }
        User entity = userMapper.toEntityFromDTO(user);
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        if(!user.getIsActive()){
            throw new NotActiveUserException("The user is not active ");
        }
        userMapper.updateEntity(request, entity);
        userRepository.save(entity);
    }

    @Transactional
    public void updatePassword(UserDTO user, UpdateProfilePassword request) {
        if (refreshTokenRepository.getAllByUser_UserId(user.getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Password does not match");
        }
        User entity = userMapper.toEntityFromDTO(user);
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        if(!user.getIsActive()){
            throw new NotActiveUserException("The user is not active");
        }
        entity.setPasswordHash(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        userMapper.updateEntity(request, entity);
        userRepository.save(entity);
    }

    @Transactional
    public UserDTO updateEmail(UserDTO user, UpdateEmailInfoForm request) {
        if (refreshTokenRepository.getAllByUser_UserId(user.getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        User entity = userMapper.toEntityFromDTO(user);
        if (userRepository.getByEmail(request.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("USer with this email already exists");
        }
        if(!user.getIsActive()){
            throw new NotActiveUserException("The user is not active");
        }
        userMapper.updateEntity(request, entity);
        return userMapper.toDTOFromEntity(userRepository.save(entity));
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (refreshTokenRepository.getAllByUser_UserId(userId) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        User entity = userRepository.findById(userId).get();
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        userRepository.deleteToken(userId);
        userRepository.delete(userRepository.findById(userId).orElseThrow(
                () -> new EntityDoesNotExistException("Can not find user with this ID")
        ));
    }

    @Transactional
    public void setIsActive(Long userId) {
        userRepository.setActive(userId);
    }

    @Transactional
    public void setNotActive(Long userId) {
        userRepository.setNotActive(userId);
    }

    @Transactional
    public void setIsShowFriends(Long userId) {
        userRepository.setShowFriends(userId);
    }

    @Transactional
    public void setNotShowFriends(Long userId) {
        userRepository.setNotShowFriend(userId);
    }


    @Transactional
    public void deleteSession(String login) {
        if (refreshTokenRepository.getAllByUser_UserId(userRepository.getByLogin(login).get().getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        User entity = userRepository.getByLogin(login).get();
        if(!entity.getIsActive()){
            throw new NotActiveUserException("The user is not active");
        }
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        userRepository.deleteToken(userRepository.getByLogin(login).get().getUserId());
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.getByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException("Can not find user with this Email")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityDoesNotExistException("Can not find user with this ID")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserByNickName(String nickname) {

        User user = userRepository.getByNickname(nickname).orElseThrow(
                () -> new EntityDoesNotExistException("The user with this nickname does not exist")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserByLogin(String login) {

        User user = userRepository.getByLogin(login).orElseThrow(
                () -> new EntityDoesNotExistException("The user with this username does not exist")
        );
        return userMapper.toDTOFromEntity(user);
    }


}