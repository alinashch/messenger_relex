package com.example.chat_relex.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Table(name = "chat_room")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_chat_room",
            joinColumns = {@JoinColumn(name = "chat_room_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;
}
