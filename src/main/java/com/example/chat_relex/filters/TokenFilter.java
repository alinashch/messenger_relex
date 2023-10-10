package com.example.chat_relex.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.chat_relex.models.Response.ErrorResponse;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.chat_relex.filters.AuthorizationFilter.AUTH_HEADER;
import static java.util.Arrays.stream;

@Component
@RequiredArgsConstructor
public class TokenFilter {

    private final UserService userService;

    private final ObjectMapper objectMapper;

    private String jwtKey="chat_relex_token";

    public void authenticate(String authHeader) {
        var decodedJWT = decodeJWT(authHeader);
        UserDTO user = userService.getUserById(Long.parseLong(decodedJWT.getSubject()));
        setAuthenticationToken(user, decodedJWT);
    }

    public DecodedJWT decodeJWT(String authHeader) {
        String token = authHeader;
        if (authHeader.contains(AUTH_HEADER)) {
            token = authHeader.substring(AUTH_HEADER.length());
        }
        var algorithm = Algorithm.HMAC256(jwtKey.getBytes());
        return JWT.require(algorithm).build().verify(token);
    }

    private void setAuthenticationToken(UserDTO user, DecodedJWT decodedJWT) {
        String username = user.getLogin();
        String password = user.getPasswordHash();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        List<SimpleGrantedAuthority> authorities = stream(roles)
                .map(SimpleGrantedAuthority::new)
                .toList();

        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void sendErrorMessage(HttpServletResponse response, String message) throws IOException {
        response.setStatus(403);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(objectMapper.writeValueAsString(new ErrorResponse(message)));
        out.flush();
    }
}