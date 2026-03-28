package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.application.*;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.interfaces.CustomerRepository;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.interfaces.EmailService;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.interfaces.ItemRepository;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.interfaces.OrderRepository;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.infrastructure.*;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@SpringBootApplication
// STRICT ISOLATION: Only scan this specific example's package!
@ComponentScan("com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
            System.out.println("--- RICH DOMAIN, THIN CONTROLLER APP RUNNING (JAVA/SPRING) ---");
        System.out.println("Swagger UI available at: http://localhost:8080/swagger-ui/index.html");
        System.out.println("------------------------------------------------");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
            .title("Grokking Software Architecture: The Rich Domain")
            .version("v1")
            .description("Demonstrating the benefits of loose coupling and rich domain models."));
    }
    // ARCHITECTURE NOTE: This is the "Composition Root."
    // We manually define the Beans here to show the Dependency Injection 
    // wiring, matching the C# builder.Services.AddScoped calls.
    @Bean
    public OrderService orderService(OrderRepository or, CustomerRepository cr, 
                                     ItemRepository ir, EmailService es) {
        return new OrderServiceImpl(or, cr, ir, es); //
    }

    @Bean
    public OrderRepository orderRepository() { return new SqlOrderRepository(); }

    @Bean
    public CustomerRepository customerRepository() { return new SqlCustomerRepository(); }

    @Bean
    public ItemRepository itemRepository() { return new SqlItemRepository(); }

    @Bean
    public EmailService emailService() { return new SmtpEmailService(); }
}