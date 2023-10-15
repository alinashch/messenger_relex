package com.example.chat_relex.mapper;


import com.example.chat_relex.models.Request.*;
import com.example.chat_relex.models.dto.CredentialsDTO;
import com.example.chat_relex.models.dto.RoleDTO;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.models.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper()
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTOFromEntity(User entity);

    List<UserDTO> toDTOFromEntityList(List<User> entity);


    User toEntityFromDTO(UserDTO dto);
    CredentialsDTO toCredentialsDTOFromDTO(UserDTO dto);

    UserRequest toRequestFromEntity( User user);


    @Mapping(target = "isVerified", expression = "java(false)")
    @Mapping(target = "isActive", expression = "java(true)")
    @Mapping(target = "isShowFriends", expression = "java(true)")
    @Mapping(target = "isCanReceiveMessageFromNotFriend", expression = "java(true)")

    User toEntityFromRequest(SignUpForm request, Set<RoleDTO> roles, String passwordHash);


    @Mapping(target = "isVerified", expression = "java(false)")
    User toEntityFromUpdateEmailInfoForm(UpdateEmailInfoForm request);
    User toEntityFromUpdateProfilePassword(UpdateProfilePassword request, Set<RoleDTO> roles, String passwordHash);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateProfileRequest request, @MappingTarget User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateProfilePassword request, @MappingTarget User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateEmailInfoForm request, @MappingTarget User entity);
}