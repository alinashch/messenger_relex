package com.example.chat_relex.mapper;

import com.example.chat_relex.models.Request.ChatRoomRequest;
import com.example.chat_relex.models.Response.ChatroomResponse;
import com.example.chat_relex.models.dto.ChatRoomDTO;
import com.example.chat_relex.models.entity.ChatRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatRoomMapper {


    ChatRoomMapper INSTANCE = Mappers.getMapper(ChatRoomMapper.class);


    ChatRoom toEntityFromDTO(ChatRoomDTO entity);

    ChatRoom toEntityFromRequest(ChatRoomRequest entity);


    ChatroomResponse toResponseFromEntity(ChatRoom entity);

}
