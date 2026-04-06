package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation;

import io.swagger.v3.oas.models.OpenAPI;
import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;
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
        LogManager.info(Demo.class, "--- Launching Rich Domain / Thin Controller (After) ---");
        LogManager.info(Demo.class, "Starting the Spring Boot Web API...");

        // 1. Boot the server directly inside this same terminal window
        ConfigurableApplicationContext context = SpringApplication.run(Demo.class, new String[]{});

        LogManager.info(Demo.class, "\n[SUCCESS] RICH DOMAIN / THIN CONTROLLER TRADITIONAL 4-LAYER ARCHITECTURE APP RUNNING (JAVA/SPRING)");
        LogManager.info(Demo.class, "Swagger UI available at: http://localhost:8080");
        LogManager.info(Demo.class, "\nPress ENTER to stop the server and return to the main menu...");

        // 2. Wait for you to test Swagger
        new Scanner(System.in).nextLine();

        // 3. Gracefully shut down so port 8080 is freed up for the next test
        LogManager.info(Demo.class, "Shutting down the Spring Boot server...");
        context.close();
        LogManager.info(Demo.class, "Server stopped successfully.");
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