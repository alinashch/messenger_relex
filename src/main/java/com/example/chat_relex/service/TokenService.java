package com.example.chat_relex.service;


import com.auth0.jwt.JWT;
import com.example.chat_relex.exceptions.ObjectNotExistsException;
import com.example.chat_relex.exceptions.TokenExpiredException;
import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.models.Response.UserResponse;
import com.example.chat_relex.models.entity.RefreshTokenEntity;
import com.example.chat_relex.models.entity.UserEntity;
import com.example.chat_relex.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserService userService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserMapper userMapper;

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final Clock clock;

    @Value("${security.jwt.secretKey}")
    private String jwtKey;

    @Value("${security.jwt.expiresMs.accessToken}")
    private Integer accessTokenExpireTime;

    @Value("${security.jwt.expiresMs.refreshToken}")
    @Setter
    private Integer refreshTokenExpireTime;

    @Transactional
    public Map<String, String> createTokens(UserResponse user) {
        var algorithm = Algorithm.HMAC256(jwtKey.getBytes());
        return Map.of(
                ACCESS_TOKEN, createAccessToken(user, algorithm),
                REFRESH_TOKEN, createRefreshToken(user)
        );
    }

    public Map<String, Object> refreshToken(String refreshToken) {
        Map<String, Object> response = new HashMap<>();
        Algorithm algorithm = Algorithm.HMAC256(jwtKey.getBytes());
        UserResponse user = verifyRefreshToken(refreshToken);
        response.put("access_token", createAccessToken(user, algorithm));
        response.put("refresh_token", refreshToken);
        return response;
    }

    private String createRefreshToken(UserResponse user) {
        String refreshToken = UUID.randomUUID().toString();
        refreshTokenRepository.save(new RefreshTokenEntity(
                refreshToken,
                LocalDateTime.now().plusDays(refreshTokenExpireTime)
                        .toInstant(clock.getZone().getRules().getOffset(Instant.now())),
              userMapper.fromUserResponseToUserEntity(  userService.getByUserId(user.getId()))));
        return refreshToken;
    }

    private String createAccessToken(UserResponse user, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpireTime))
                .withIssuer("chat-relex-service")
                .withClaim("email", user.getEmail())
                .withClaim("login", user.getLogin())
                .withClaim("nickname", user.getNickname())
                .withClaim("fullName", user.getName().concat(" ").concat(user.getSurname()))
                .sign(algorithm);
    }

    private UserResponse verifyRefreshToken(String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findById(refreshToken).orElseThrow(
                () -> new ObjectNotExistsException("Cannot find refresh token for this user")
        );
        Instant now = Instant.now(clock);
        if (refreshTokenEntity.getDateExpire().isBefore(now)) {
            throw new TokenExpiredException("Token has been already expired");
        }
        return userMapper.fromUserEntityToUserResponse( refreshTokenEntity.getUser());
    }
}
