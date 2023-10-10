package com.example.chat_relex.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "chat_room")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ChatRoomId;

    @Column(nullable = false)
    private String chatId;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private Long recipientId;
}
