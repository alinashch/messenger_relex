package com.example.chat_relex.service;

import com.example.chat_relex.exceptions.EntityDoesNotExistException;
import com.example.chat_relex.mapper.MessageStatusMapper;
import com.example.chat_relex.mapper.RoleMapper;
import com.example.chat_relex.models.dto.MessageStatusDTO;
import com.example.chat_relex.models.dto.RoleDTO;
import com.example.chat_relex.models.entity.MessageStatus;
import com.example.chat_relex.models.entity.Role;
import com.example.chat_relex.repository.MessageStatusRepository;
import com.example.chat_relex.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.chat_relex.models.constant.MessageStatus.DELIVERED;
import static com.example.chat_relex.models.constant.MessageStatus.RECEIVED;

@Service
@AllArgsConstructor
public class MessageStatusService {

    private final MessageStatusRepository messageStatusRepository;

    private final MessageStatusMapper messageStatusMapper;

    public MessageStatusDTO getMessageStatusReceived() {
        MessageStatus role = messageStatusRepository.findByMessageStatus("RECEIVED").orElseThrow(
                () -> new EntityDoesNotExistException("Роль с данным именем не существует")
        );
        return messageStatusMapper.toDTOFromEntity(role);
    }

    public MessageStatusDTO getMessageStatusDelivered() {
        MessageStatus role = messageStatusRepository.findByMessageStatus("DELIVERED").orElseThrow(
                () -> new EntityDoesNotExistException("Роль с данным именем не существует")
        );
        return messageStatusMapper.toDTOFromEntity(role);
    }

    public Long getMessageStatusID(MessageStatus messageStatus){
      return   messageStatusRepository.findByMessageStatus(messageStatus.getMessageStatus()).get().getMessageStatusId();
    }
}
