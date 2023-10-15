package com.example.chat_relex.confirurations;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@OpenAPIDefinition(
        info = @Info(
                title = "Relex Messenger",
                description = "Описание всех эндпоинтов сервиса \"Relex Messenger\"",
                contact = @Contact(name = "Алина Щербинина", email = "alina.scsh.28072002@gmail.com"),
                version = "1.0.1"),
        security = @SecurityRequirement(name = "bearerAuth"))
public class SpringdocConfiguration {
}