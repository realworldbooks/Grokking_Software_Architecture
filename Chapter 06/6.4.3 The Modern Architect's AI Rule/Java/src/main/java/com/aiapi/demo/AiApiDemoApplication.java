package com.aiapi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This single annotation tells Java: "I am a web server. 
// Please scan all my folders for Controllers and wire them up automatically."
@SpringBootApplication
public class AiApiDemoApplication {

    public static void main(String[] args) {
        // This line actually boots up the Tomcat web server and launches your API
        SpringApplication.run(AiApiDemoApplication.class, args);
        
        System.out.println("--- Spring Boot AI API is running! ---");
        System.out.println("Check the OpenAPI spec at: http://localhost:8080/v3/api-docs");
    }
}
