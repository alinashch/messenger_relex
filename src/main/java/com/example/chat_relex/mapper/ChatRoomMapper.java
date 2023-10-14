package com.example.chat_relex.mapper;

import com.example.chat_relex.models.Request.ChatRoomRequest;
import com.example.chat_relex.models.Response.ChatroomResponse;
import com.example.chat_relex.models.dto.ChatRoomDTO;
import com.example.chat_relex.models.dto.MessageDTO;
import com.example.chat_relex.models.entity.ChatRoom;
import com.example.chat_relex.models.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatRoomMapper {


    ChatRoomMapper INSTANCE = Mappers.getMapper(ChatRoomMapper.class);

    ChatRoomDTO toDTOFromEntity(ChatRoom entity);

    ChatRoom toEntityFromDTO(ChatRoomDTO entity);

    ChatRoom toEntityFromRequest(ChatRoomRequest entity);

    ChatRoom toEntityFromResponse(ChatroomResponse chatroomResponse);

    ChatroomResponse toResponseFromEntity(ChatRoom entity);

}
