package com.example.usersubscriptionservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("User Subscription Service API")
                .version("1.0")
                .description("API для управления пользователями и их подписками")
                .contact(new Contact()
                    .name("Dmitriy Dubov")
                    .email("schliffen@mail.ru")));
    }
}
