package com.example.chat_relex.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    public final static String AUTH_HEADER = "Bearer ";

    private final TokenFilter tokenFilter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(AUTH_HEADER)) {
            try {
                tokenFilter.authenticate(authHeader);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                tokenFilter.sendErrorMessage(response, e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}