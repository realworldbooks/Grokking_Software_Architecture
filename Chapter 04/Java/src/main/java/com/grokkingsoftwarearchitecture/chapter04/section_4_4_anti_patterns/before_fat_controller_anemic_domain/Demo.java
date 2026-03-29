package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import java.util.Scanner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class Demo {

    public static void run() {
        System.out.println("--- Launching 'The Fat Controller' (Anti-Pattern) ---");
        System.out.println("Starting the Spring Boot Web API...");

        // 1. Start the server and capture the running context
        ConfigurableApplicationContext context = SpringApplication.run(Demo.class, new String[]{});

        System.out.println("\n[SUCCESS] FAT CONTROLLER APP RUNNING (JAVA/SPRING)");
        System.out.println("Swagger UI available at: http://localhost:8080");
        System.out.println("\nPress ENTER to stop the server and return to the main menu...");

        // 2. Pause the menu while you test the endpoints in your browser
        new Scanner(System.in).nextLine();
        // 3. Cleanly shut down the server to prevent zombie processes
        System.out.println("Shutting down the Spring Boot server...");
        context.close();
        System.out.println("Server stopped successfully. Returning to menu...");
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

@Controller
class SwaggerRedirectController {
    @GetMapping("/")
    public String redirect() {
        return "redirect:/swagger-ui/index.html";
    }
}