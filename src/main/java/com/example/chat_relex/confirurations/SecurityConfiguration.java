package com.example.chat_relex.confirurations;

import com.example.chat_relex.components.ConcurrentSessionStrategy;
import com.example.chat_relex.components.SecurityErrorHandler;
import com.example.chat_relex.filters.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@EnableJdbcHttpSession
@RequiredArgsConstructor
public class SecurityConfiguration implements WebMvcConfigurer {


    private final static String USER = "USER";
    private final static String ADMIN = "ADMIN";


    private final AuthorizationFilter authorizationFilter;

    private final ConcurrentSessionStrategy concurrentSessionStrategy;
    private final SessionRegistry sessionRegistry;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(GET, "/auth/credentials").hasAnyAuthority(USER)
                .requestMatchers(POST, "/auth/resend-code").hasAnyAuthority(USER)
                .requestMatchers(PUT, "/auth/profile/**").hasAnyAuthority(USER)
                .requestMatchers(DELETE, "/auth/*").hasAnyAuthority(USER)

                .anyRequest().authenticated();

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                //Возвращаем при логауте 200(по умолчанию возвращается 203)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                //Инвалидируем сессию при логауте
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                //Удаляем всю информацию с фронта при логауте(т.е. чистим куки, хидеры и т.д.)
                .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL)))
                .permitAll().and()
                //Включаем менеджер сессий(для контроля количества сессий)
                .sessionManagement()
                //Указываем макимальное возможное количество сессий(тут указано не 1, т.к. мы будем пользоваться своей кастомной стратегией, объяснение будет ниже)
                .maximumSessions(3)
                //При превышение количества активных сессий(3) выбрасывается исключение  SessionAuthenticationException
                .maxSessionsPreventsLogin(true)
                //Указываем как будут регестрироваться наши сессии(тогда во всем приложение будем использовать именно этот бин)
                .sessionRegistry(sessionRegistry).and()
                //Добавляем нашу кастомную стратегию для проверки кличества сессий
                .sessionAuthenticationStrategy(concurrentSessionStrategy)
                //Добавляем перехватчик для исключений
                .sessionAuthenticationFailureHandler(new SecurityErrorHandler());
        return http.build();
    }



    //для инвалидации сессий при логауте
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Bean
    public static SessionRegistry sessionRegistry(JdbcIndexedSessionRepository sessionRepository) {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }
}