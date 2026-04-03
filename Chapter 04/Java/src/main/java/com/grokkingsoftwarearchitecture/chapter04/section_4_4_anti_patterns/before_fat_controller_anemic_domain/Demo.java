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
import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

@SpringBootApplication
public class Demo {

    public static void run() {
        LogManager.info(Demo.class, "--- Launching 'The Fat Controller' (Anti-Pattern) ---");
        LogManager.info(Demo.class, "Starting the Spring Boot Web API...");

        // 1. Start the server and capture the running context
        ConfigurableApplicationContext context = SpringApplication.run(Demo.class);

        LogManager.info(Demo.class, "\n[SUCCESS] FAT CONTROLLER APP RUNNING (JAVA/SPRING)");
        LogManager.info(Demo.class, "Swagger UI available at: http://localhost:8080");
        LogManager.info(Demo.class, "\nPress ENTER to stop the server and return to the main menu...");

        // 2. Pause the menu while you test the endpoints in your browser
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        // 3. Cleanly shut down the server to prevent zombie processes
        LogManager.info(Demo.class, "Shutting down the Spring Boot server...");
        context.close();
        LogManager.info(Demo.class, "Server stopped successfully. Returning to menu...");
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