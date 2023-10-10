package com.example.chat_relex.repository;

import com.example.chat_relex.models.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {


    @Modifying
    @Query(value = """
                    INSERT INTO refresh_token(token, valid_till, user_id)
                    VALUES(:refreshToken, :expireDate, :userId)""", nativeQuery = true)
    void saveNewRefreshToken(@Param("refreshToken") UUID refreshToken,
                             @Param("expireDate") Instant expireDate,
                             @Param("userId") Long userId);

    @Query(value = """
                    SELECT COUNT(b) FROM refresh_token b WHERE  b.user_id =:userId """, nativeQuery = true)
    long getAllByUser_UserId( @Param("userId") Long userId);
}