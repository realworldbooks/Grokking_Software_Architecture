package aiapi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Execution Layer.
 */
@SpringBootApplication
public class Demo {
    public static void main(String[] args) {
        System.out.println("--- STARTING AI-DRIVEN API DEMO ---");
        System.out.println("Swagger UI will be available at: http://localhost:8080/swagger-ui.html");
        
        SpringApplication.run(Demo.class, args);
    }
}