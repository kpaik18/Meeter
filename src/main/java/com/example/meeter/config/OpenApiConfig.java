package com.example.meeter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Meeter API")
                        .description("""
                                API for Meeter application, which enables users to create, manage, 
                                and share their available meeting slots with others. 
                                Users can easily create meetings, edit details, 
                                and share unique links for reservation, 
                                streamlining the process of scheduling and reserving meetings.
                                """)
                        .version("1.0"));
    }
}
