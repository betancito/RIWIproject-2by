package com.riwi.project.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "RiwiProjects",
        version = "v1",
        description = "RIWI Projects API"
),servers = {
        @Server(url =  "http://localhost:8080/", description = "URL LOCAL SERVER")
}
)
@SecurityRequirement(name = "bearerAuth")
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customSwagger(){
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes("Token",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .name("Authorization")));
    }
}