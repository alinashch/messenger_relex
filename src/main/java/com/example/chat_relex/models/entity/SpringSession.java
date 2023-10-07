package com.example.chat_relex.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "spring_session")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SpringSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long primaryId;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false)
    private Long creationTime;

    @Column(nullable = false)
    private Long lastAccessTime;

    @Column(nullable = false)
    private Integer maxInactiveInterval;

    @Column(nullable = false)
    private Long expiryTime;

    @Column
    private String principalName;
}
