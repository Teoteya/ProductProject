package com.example.product_project.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Приложение. Система управления продуктами")
                        .version("1.0")
                        .description("Документация для API. API предоставляет доступ к управлению продуктами, их количеством, статусом и значением")
                        .contact(new Contact()
                                .name("Команда разработки приложения")
                                .email("teyarad@bk.ru")
                        ));
    }
}