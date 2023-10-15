package com.example.chat_relex.controller;
import com.example.chat_relex.models.Request.*;
import com.example.chat_relex.models.dto.CredentialsDTO;
import com.example.chat_relex.models.dto.ExceptionDTO;
import com.example.chat_relex.models.dto.TokensDTO;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.service.AuthService;
import com.example.chat_relex.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.example.chat_relex.models.constant.Tag.USER;
import static org.apache.naming.ResourceRef.AUTH;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    @GetMapping("/credentials")
    @Operation(summary = "Получение информации о пользователе", tags = USER, responses = {
            @ApiResponse(responseCode = "200", description = "Получение данные", content = {
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

    @PutMapping("/profile/update/personal-information")
    @Operation(summary = "Обновление информации о пользователе", tags = USER, responses = {
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
    public ResponseEntity<?> updateProfileInformation(@RequestBody @Valid UpdateProfileRequest request,
                                                      Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        System.out.println((String) authentication.getPrincipal());
        userService.updateProfile(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/password")
    @Operation(summary = "Обновление информации о пароле", tags = USER, responses = {
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
    public ResponseEntity<?> updateProfilePassword(@RequestBody @Valid UpdateProfilePassword request,
                                                   Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.updatePassword(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/email")
    @Operation(summary = "Обновление информации о email", tags = USER, responses = {
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
    public ResponseEntity<?> updateProfileEmail(@RequestBody @Valid UpdateEmailInfoForm request,
                                                   Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        authService.resendCode( request, user);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/set-active")
    @Operation(summary = "Обновление информации о статусе аккаунта", tags = USER, responses = {
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
    public ResponseEntity<?> updateProfileSetIsActive(Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.setIsActive(user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/set-not-active")
    @Operation(summary = "Обновление информации о статусе аккаунта", tags = USER, responses = {
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
    public ResponseEntity<?> updateProfileSetNotActive(Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.setNotActive(user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/friends/update/set-show-friends")
    @Operation(summary = "Обновление информации о показе списка друзей", tags = USER, responses = {
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
    public ResponseEntity<?> updateIsShowFriends(Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.setIsShowFriends(user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/set-not-show-friends")
    @Operation(summary = "Обновление информации о показе списка друзей", tags = USER, responses = {
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
    public ResponseEntity<?> updateNotShowFriends(Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.setNotShowFriends(user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление пользователя", tags = USER, responses = {
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
    public ResponseEntity<?> deleteUser( Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.deleteUser(user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }

}
