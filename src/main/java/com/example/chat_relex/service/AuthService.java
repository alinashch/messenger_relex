package com.example.chat_relex.service;

import com.example.chat_relex.Template.EmailTemplate;
import com.example.chat_relex.exceptions.WrongCredentialsException;
import com.example.chat_relex.exceptions.WrongInputLoginException;
import com.example.chat_relex.models.Request.LoginForm;
import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.models.dto.TokensDTO;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.models.dto.VerificationEmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final VerificationService verificationService;
    private final EmailService emailService;
    private final TokenUserService tokenService;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${emailTemplate.mainPage}")
    private String mainPage;

    @Value("${emailTemplate.verificationLink}")
    private String verificationLink;

    @Transactional
    public TokensDTO signUp(SignUpForm request) {
        UserDTO registeredUser = userService.registerUser(request);
        UUID code = verificationService.createCodeAndSave(registeredUser);
        //sendCode(registeredUser, code);
        return tokenService.createTokens(registeredUser);
    }

    @Async
    void sendCode(UserDTO user, UUID code) {
        emailService.sendTemplate(
                user.getEmail(),
                "Подтверждение регистрации в чате relex",
                EmailTemplate.VERIFICATION_USER,
                new VerificationEmailDTO(
                        user.getFirstName() + " " + user.getLastName(),
                        mainPage,
                        verificationLink + "/" + code.toString()
                ));
    }

    @Transactional
    public void resendCode(UserDTO user) {
        UUID code = verificationService.resendCode(user);
        sendCode(user, code);
    }

    @Transactional
    public void verifyUser(String code) {
        Long userId = verificationService.getVerificationUserId(code);
        userService.verifyUserById(userId);
    }

    @Transactional
    public TokensDTO login(LoginForm request) {
        if(userService.getUserByLogin(request.getLogin())==null){
            throw new WrongCredentialsException("Неправильный логин ");
        }
        UserDTO user = userService.getUserByLogin(request.getLogin());
        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new WrongInputLoginException("Неправильный пароль");
        }
        return tokenService.createTokens(user);
    }
}