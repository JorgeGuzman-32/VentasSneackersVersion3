package com.example.venta_sneackers.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        .info(new Info()
            .title("API de Venta de Sneackers")
            .version("1.0")
            .description("Documentación de la API para el sistema de ventas de sneackers"));
    }
}