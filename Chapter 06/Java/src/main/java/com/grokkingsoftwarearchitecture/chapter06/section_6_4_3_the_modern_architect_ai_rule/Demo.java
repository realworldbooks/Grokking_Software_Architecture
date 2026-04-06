package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.grokkingsoftwarearchitecture.chapter06.shared.LogManager;

/**
 * The Execution Layer.
 * Replaces Demo.cs/Program.cs to maintain consistency with the rest of the book's architecture.
 * Configures the Spring Web container and automatically spins up Swagger 
 * to act as the bridge between our code and the AI Agent.
 */
@SpringBootApplication
public class Demo {

    public static void run() {
        // Use System.out for the startup banner to avoid LogManager metadata spam
        System.out.println("\n--- STARTING THE MODERN AI ARCHITECT DEMO ---");
        System.out.println("Goal: Turn our Java codebase into a perfect LLM Prompt.");
        
        // Spring Boot defaults to port 8080
        System.out.println("Swagger UI will be available at: http://localhost:8080/swagger-ui/index.html");
        
        System.out.println("\n--> Open the URL above and read the descriptions.");
        System.out.println("--> Notice how we are commanding the AI exactly how to behave!\n");
        
        // This single line builds the container, registers all DI components, 
        // configures OpenAPI, and starts the web server.
        SpringApplication.run(Demo.class);
    }
}