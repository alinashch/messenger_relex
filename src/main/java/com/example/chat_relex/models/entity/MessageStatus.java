package com.example.chat_relex.models.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "message_status")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageStatusId;

    @Column(nullable = false, unique = true)
    private String messageStatus;
}
