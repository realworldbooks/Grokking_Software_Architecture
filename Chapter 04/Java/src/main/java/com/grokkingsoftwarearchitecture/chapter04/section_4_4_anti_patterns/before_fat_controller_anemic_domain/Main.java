package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("--- FAT CONTROLLER APP RUNNING (JAVA/SPRING) ---");
        System.out.println("Swagger UI available at: http://localhost:8080/swagger-ui/index.html");
        System.out.println("------------------------------------------------");
    }

    // ARCHITECTURAL NOTE: Swagger Configuration.
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
            .title("Grokking Software Architecture: The Fat Controller")
            .version("v1")
            .description("Demonstrating the pitfalls of tight coupling and anemic models."));
    }
}