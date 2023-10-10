package com.example.chat_relex.models.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MessageStatusDTO {

    private Long messageStatusId;

    private String messageStatus;
}
