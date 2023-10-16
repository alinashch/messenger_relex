package com.example.chat_relex.models.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {

    private Long messageId;

    private String message;

    private String sender;

}
