package com.example.chat_relex.controller;

import com.example.chat_relex.models.Request.LoginForm;
import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.models.Request.UpdateEmailInfoForm;
import com.example.chat_relex.models.Request.UpdatePersonalInfoForm;
import com.example.chat_relex.models.Response.UserResponse;
import com.example.chat_relex.models.entity.UserEntity;
import com.example.chat_relex.service.TokenService;
import com.example.chat_relex.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;


    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        UserResponse user= userService.save(signUpForm);
        return ResponseBuilder.build(CREATED, Map.of(
                "tokens", tokenService.createTokens(user)
        ));
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> register() {
        List<UserResponse> userResponses = userService.findAll();
        return ResponseBuilder.build(OK, userResponses);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm) {
        UserResponse user = userService.login(loginForm);
        return ResponseBuilder.build(OK, Map.of(
                "tokens", tokenService.createTokens(user)
        ));
    }

    @DeleteMapping("/{UserId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("UserId") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{UserId}")
    public ResponseEntity<?> getUSer(@PathVariable("UserId") Long id) {
       UserResponse userResponses= userService.getByUserId(id);
        return ResponseBuilder.build(OK, userResponses);
    }

    @PostMapping("/updateName")
    public ResponseEntity<?> updateName(@Valid @RequestBody UpdatePersonalInfoForm updatePersonalInfoForm) {
         userService.updateName(updatePersonalInfoForm.getId(), updatePersonalInfoForm.getValue());
        return ResponseBuilder.build(OK);
    }

    @PostMapping("/updateSurname")
    public ResponseEntity<?> updateSurname(@Valid @RequestBody UpdatePersonalInfoForm updatePersonalInfoForm) {
        userService.updateSurname(updatePersonalInfoForm.getId(), updatePersonalInfoForm.getValue());
        return ResponseBuilder.build(OK);
    }

    @PostMapping("/updateNickname")
    public ResponseEntity<?> updateNickname(@Valid @RequestBody UpdatePersonalInfoForm updatePersonalInfoForm) {
        userService.updateNickname(updatePersonalInfoForm.getId(), updatePersonalInfoForm.getValue());
        return ResponseBuilder.build(OK);
    }

    @PostMapping("/updateEmail")
    public ResponseEntity<?> updateNickname(@Valid @RequestBody UpdateEmailInfoForm updateEmailInfoForm) {
        userService.updateEmail(updateEmailInfoForm.getId(), updateEmailInfoForm.getEmail());
        return ResponseBuilder.build(OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("refreshToken") @NotBlank String refreshToken) {
        return ResponseBuilder.build(OK, tokenService.refreshToken(refreshToken));
    }
}
