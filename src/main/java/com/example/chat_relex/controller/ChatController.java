package com.example.chat_relex.controller;


import com.example.chat_relex.exceptions.EmailNotVerification;
import com.example.chat_relex.exceptions.NotActiveUser;
import com.example.chat_relex.models.Request.StartChatForm;
import com.example.chat_relex.models.Response.ChatroomResponse;
import com.example.chat_relex.models.dto.*;
import com.example.chat_relex.service.ChatRoomService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.chat_relex.models.constant.Tag.CHAT;
import static org.apache.naming.ResourceRef.AUTH;
import static org.springframework.http.HttpStatus.OK;


@RestController
@AllArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final UserService userService;

    private final ChatRoomService chatRoomService;


    @PostMapping("/start")
    @Operation(summary = "Старт нового чата", tags = CHAT, responses = {
            @ApiResponse(responseCode = "201", description = "Пользователь зарегистрирован", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokensDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "409", description = "Пользователь  указанным ником не найден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))}),
            @ApiResponse(responseCode = "411", description = "Не подтверждена почта", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmailNotVerification.class))
            }),
            @ApiResponse(responseCode = "412", description = "Не активный пользователь", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotActiveUser.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> startNewChat(@RequestBody @Valid StartChatForm startChatForm, Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        ChatroomResponse chatroomResponse=chatRoomService.startNewChat(startChatForm, user);
        return ResponseBuilder.build(OK,chatroomResponse);
    }

}
