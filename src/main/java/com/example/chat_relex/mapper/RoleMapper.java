package com.example.chat_relex.mapper;

import com.example.chat_relex.models.dto.RoleDTO;
import com.example.chat_relex.models.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTOFromEntity(Role entity);

}