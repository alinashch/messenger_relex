package com.example.chat_relex.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Table(name = "message")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MessageId;

    @Column(nullable = false)
    private String chatId;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private Long recipientId;

    @Column(nullable = false)
    private String senderNickname;

    @Column(nullable = false)
    private String recipientNickname;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_status_id")
    private MessageStatus status;

}
