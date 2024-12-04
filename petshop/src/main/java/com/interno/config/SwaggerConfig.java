package com.interno.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("petshop")
                .pathsToMatch("/**")
                .packagesToScan("com.interno.resources")
                .build();
    }

    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info().title("Petshohp")
                                .description("PetshohpOS APIs")
                                .version("1.0")
                                .contact(new Contact()
                                    .name("Curso Spring")
                                    .url("http://www.petshop.com.br")
                                    .email("petshop@email.com")));
    }

}
