package com.example.chat_relex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionsManager {

    private final FindByIndexNameSessionRepository sessionRepository;

    @Transactional
    public void deleteSessionExceptCurrentByUser(String username) {
        log.debug("deleteSessionExceptCurrent#user: {}", username);
        //Получаем session id текущего пользователя
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        //Удаляем все сессии кроме текущей
        sessionRepository.findByPrincipalName(username)
                .keySet().stream()
                .filter(key -> !sessionId.equals(key))
                .forEach(key -> sessionRepository.deleteById((String) key));
    }

}
