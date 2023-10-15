package com.example.chat_relex.controller;
import static com.example.chat_relex.models.constant.Tag.FRIEND;
import static org.springframework.http.HttpStatus.OK;

import com.example.chat_relex.models.dto.ExceptionDTO;
import com.example.chat_relex.models.dto.FriendDTO;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.models.entity.User;
import com.example.chat_relex.service.FriendService;
import com.example.chat_relex.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @GetMapping("")
    @Operation(summary = "Выводит всех друзей пользователя", tags = FRIEND, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает всех друзей", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FriendDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getAllFriends(Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        return ResponseBuilder.build(OK, friendService.getFriends(user.getUserId()));
    }

    @GetMapping("/{nickname}/all-friends")
    @Operation(summary = "Выводит всех друзей пользователя", tags = FRIEND, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает всех друзей", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FriendDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> showUserFriends(@PathVariable("nickname") String nickname, Authentication authentication) {
        userService.getUserByLogin((String) authentication.getPrincipal());
        List<UserDTO> userList=  friendService.showUserFriends( nickname);
        return ResponseBuilder.build(OK, userList);
    }

    @PostMapping("/{nickname}")
    @Operation(summary = "Добавить пользователя в друзья", tags = FRIEND, responses = {
            @ApiResponse(responseCode = "200", description = "Пользователь добавлен", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователя не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "409", description = "Пользователь уже в друзьях", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> addFriend(@PathVariable("nickname") String nickname, Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        friendService.addFriend(user, nickname);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @DeleteMapping("/{nickname}")
    @Operation(summary = "Удалить пользователя из друзей", tags = FRIEND, responses = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален из друзей", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователя не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "405", description = "Пользователя не было в друзьях", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> deleteFriend(@PathVariable("nickname") String nickname, Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        friendService.deleteFriend(user, nickname);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }
}