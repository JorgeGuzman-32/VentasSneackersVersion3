package com.example.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para el API Gateway.
 * Este gateway actúa como proxy para las APIs de los microservicios.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Venta Sneackers API Gateway")
                        .description("API Gateway que enruta las solicitudes hacia los microservicios de Venta Sneackers")
                        .version("1.0.0"));
    }
}
