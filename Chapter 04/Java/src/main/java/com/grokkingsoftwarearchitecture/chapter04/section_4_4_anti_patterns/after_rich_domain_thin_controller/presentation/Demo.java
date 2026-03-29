package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import java.util.Scanner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// This annotation makes this file act exactly like Program.cs in C#
@SpringBootApplication
// If your Domain/Application layers are in different packages, tell Spring where to find them for Dependency Injection
@ComponentScan(basePackages = "com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller")
public class Demo {

    // This acts as the "button" your root menu clicks
    public static void run() {
        System.out.println("--- Launching Rich Domain / Thin Controller (After) ---");
        System.out.println("Starting the Spring Boot Web API...");

        // 1. Boot the server directly inside this same terminal window
        ConfigurableApplicationContext context = SpringApplication.run(Demo.class, new String[]{});

        System.out.println("\n[SUCCESS] CLEAN ARCHITECTURE APP RUNNING (JAVA/SPRING)");
        System.out.println("Swagger UI available at: http://localhost:8080");
        System.out.println("\nPress ENTER to stop the server and return to the main menu...");

        // 2. Wait for you to test Swagger
        new Scanner(System.in).nextLine();

        // 3. Gracefully shut down so port 8080 is freed up for the next test
        System.out.println("Shutting down the Spring Boot server...");
        context.close();
        System.out.println("Server stopped successfully.");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
            .title("Rich Domain / Thin Controller API")
            .version("v1")
            .description("Fat Controller and Anemic Domain eliminated."));
    }
}
@Controller
class SwaggerRedirectController {
    @GetMapping("/")
    public String redirect() {
        return "redirect:/swagger-ui/index.html";
    }
}