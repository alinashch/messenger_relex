package com.example.chat_relex.mapper;


import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.models.entity.Verification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.time.Instant;
import java.util.UUID;

@Mapper(uses = UserMapper.class)
public interface VerificationMapper {

    VerificationMapper INSTANCE = Mappers.getMapper(VerificationMapper.class);

    Verification toEntityFromParams(UserDTO user, Instant validTill, UUID code);
}