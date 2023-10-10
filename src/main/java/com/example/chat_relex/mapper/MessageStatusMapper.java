package com.example.chat_relex.mapper;

import com.example.chat_relex.models.entity.MessageStatus;
import com.example.chat_relex.models.dto.MessageStatusDTO;
import com.example.chat_relex.models.dto.RoleDTO;
import com.example.chat_relex.models.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper

public interface MessageStatusMapper {

    MessageStatusMapper INSTANCE = Mappers.getMapper(MessageStatusMapper.class);

    MessageStatusDTO toDTOFromEntity(MessageStatus entity);

    MessageStatus toEntityFromDTO(MessageStatusDTO entity);

}
