package com.epam.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "REST API", version = "1.0",
        contact = @Contact(name = "Osama Khan", email = "mohammadosama_khan@epam.com")))
public class OpenApiConfig {
}
