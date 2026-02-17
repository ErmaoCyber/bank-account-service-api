package com.example.bank.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .tags(List.of(
                        new Tag().name("1. Customers").description("Customer management APIs"),
                        new Tag().name("2. Accounts").description("Account management APIs"),
                        new Tag().name("3. Transfers").description("Money transfer APIs")
                ));
    }
}
