package com.example.chat_relex.service;

import com.example.chat_relex.exceptions.PasswordDoesNotMatchException;
import com.example.chat_relex.exceptions.UserAlreadyExistsException;
import com.example.chat_relex.exceptions.WrongInputLoginException;
import com.example.chat_relex.models.Request.LoginForm;
import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.models.Response.UserResponse;
import com.example.chat_relex.models.entity.UserEntity;
import com.example.chat_relex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public List<UserResponse> findAll() {
        return userMapper.fromEntityToResponseList(userRepository.findAll());
    }


    @Override
    @Transactional
    public UserResponse save(SignUpForm signUpForm) {
        if (userRepository.getByEmail(signUpForm.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with following email already exists");
        }
        if (userRepository.getByLogin(signUpForm.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with following login already exists");
        }

        if (!signUpForm.getPassword().equals(signUpForm.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Passwords do not match");
        }

        String passwordHash = bCryptPasswordEncoder.encode(signUpForm.getPassword());

        UserEntity user = userRepository.save(userMapper.fromSignUpFormToEntity(signUpForm, passwordHash));
        return userMapper.fromUserEntityToUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserResponse getByUserId(Long id) {
        return userMapper.fromUserEntityToUserResponse(userRepository.getById(id));
    }

    @Override
    @Transactional
    public UserResponse getByUserIdBeforeAuthentication (Long id,  Authentication authentication) {
        return userMapper.fromUserEntityToUserResponse(userRepository.getById((Long) authentication.getPrincipal()));
    }

    @Override
    public UserResponse login(LoginForm loginForm) {
        if (userRepository.getByLogin(loginForm.getLogin()).isEmpty()) {
            throw new WrongInputLoginException("Not found user with this login ");
        }
        UserEntity user = userRepository.getByLogin(loginForm.getLogin()).get();
        if (!bCryptPasswordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new WrongInputLoginException("Wrong login or password");
      }
        return userMapper.fromUserEntityToUserResponse(user);
    }

    @Override
    @Transactional
    public void updateName(Long user_id, String name) {
        userRepository.updateName(user_id, name);
    }

    @Override
    @Transactional
    public void updateNickname(Long user_id, String nickname) {
        userRepository.updateNickname(user_id, nickname);
    }

    @Override
    @Transactional
    public void updateEmail(Long user_id, String email) {
        userRepository.updateEmail(user_id, email);
    }

    @Override
    @Transactional
    public void updateSurname(Long user_id, String surname) {
        userRepository.updateSurname(user_id, surname);
    }

}
