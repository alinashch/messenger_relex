package com.example.chat_relex.mapper;


import com.example.chat_relex.models.Request.SignUpForm;
import com.example.chat_relex.models.Request.UpdateProfilePassword;
import com.example.chat_relex.models.Request.UpdateProfileRequest;
import com.example.chat_relex.models.Request.UserRequest;
import com.example.chat_relex.models.dto.CredentialsDTO;
import com.example.chat_relex.models.dto.RoleDTO;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.models.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper()
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTOFromEntity(User entity);

    User toEntityFromDTO(UserDTO dto);
    CredentialsDTO toCredentialsDTOFromDTO(UserDTO dto);

    UserRequest toRequestFromEntity( User user);


    @Mapping(target = "isVerified", expression = "java(false)")
    User toEntityFromRequest(SignUpForm request, Set<RoleDTO> roles, String passwordHash);
    User toEntityFromUpdateProfilePassword(UpdateProfilePassword request, Set<RoleDTO> roles, String passwordHash);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateProfileRequest request, @MappingTarget User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateProfilePassword request, @MappingTarget User entity);
}