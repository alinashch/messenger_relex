package com.example.chat_relex.controller;

import com.example.chat_relex.models.Request.*;
import com.example.chat_relex.models.dto.CredentialsDTO;
import com.example.chat_relex.models.dto.ExceptionDTO;
import com.example.chat_relex.models.dto.TokensDTO;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.service.AuthService;
import com.example.chat_relex.service.TokenUserService;
import com.example.chat_relex.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.apache.naming.ResourceRef.AUTH;
import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenUserService tokenService;
    private final UserService userService;

    @GetMapping("/credentials")
    @Operation(summary = "Получение информации о пользователе", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Получение кредов", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CredentialsDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getCredentials(Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        return ResponseBuilder.build(OK, userService.getCredentials(user));
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя", tags = AUTH, responses = {
            @ApiResponse(responseCode = "201", description = "Пользователь зарегистрирован", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokensDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(
                    responseCode = "409",
                    description = "Пользователь с указанной почтой или указанным ником уже существует",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
                    })
    })
    @SecurityRequirements
    public ResponseEntity<?> signUpNewUserUsingForm(@RequestBody @Valid SignUpForm request) {
        return ResponseBuilder.build(OK, authService.signUp(request));
    }


    @PostMapping("/verify/{code}")
    @Operation(summary = "Верификация пользователя", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Пользователь верифицирован", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(
                    responseCode = "404", description = "Ссылка невалидна", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(
                    responseCode = "405", description = "Истекло время действия ссылки", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> verifyUser(@PathVariable("code") String code) {
        authService.verifyUser(code);
        return ResponseBuilder.buildWithoutBodyResponse(CREATED);
    }

    @PostMapping("/resend-code")
    @Operation(summary = "Повторная отправка кода", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Код повторно отправлен", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "405", description = "Пользователь уже верифицирован", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> resendCode(Authentication authentication) {
        UserDTO user = userService.getUserByEmail((String) authentication.getPrincipal());
        authService.resendCode(user);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }


    @PostMapping("/login")
    @Operation(summary = "Вход пользователя", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно вошел в систему", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokensDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Неправильный пароль или логин", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginForm request) {

        return ResponseBuilder.build(OK, authService.login(request));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Обновление access токена", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращение обновленных токенов", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokensDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Токен отсутствует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Токен не валиден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Токен не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseBuilder.build(OK, tokenService.refreshAccessToken(request));
    }

    @PutMapping("/profile/update/personal-information")
    @Operation(summary = "Обновление информации о пользователе", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "409",
                    description = "Пользователь c указанным ником уже существует",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDTO.class))
                    })
    })
    @SecurityRequirements
    public ResponseEntity<?> updateProfileInformation(@RequestBody @Valid UpdateProfileRequest request,
                                                      Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.updateProfile(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/password")
    @Operation(summary = "Обновление информации о пользователе", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> updateProfilePassword(@RequestBody @Valid UpdateProfilePassword request,
                                                   Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.updatePassword(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление пользователя", tags = AUTH, responses = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователя не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> deleteUser( Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.deleteUser(user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }
    @PostMapping("/signOut")
    @Operation(summary = "Закрытие сессии ", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Закрытие сессии", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokensDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> signOut(Authentication authentication) {
        userService.deleteSession((String) authentication.getPrincipal());
        SecurityContextHolder.clearContext();
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }

}