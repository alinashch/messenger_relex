package com.example.chat_relex.mapper;

import com.example.chat_relex.models.Request.MessageRequest;
import com.example.chat_relex.models.Response.MessageResponse;
import com.example.chat_relex.models.dto.MessageDTO;
import com.example.chat_relex.models.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageDTO toDTOFromEntity(Message entity);

    Message toEntityFromDTO(MessageDTO entity);

    Message toEntityFromResponse(MessageResponse entity);

    Message toEntityFromRequest(MessageRequest entity);

}
