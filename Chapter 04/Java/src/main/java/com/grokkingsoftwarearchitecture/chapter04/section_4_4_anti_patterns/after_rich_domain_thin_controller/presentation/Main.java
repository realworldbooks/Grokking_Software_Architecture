package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.business_logic.*;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.data_access.*;

@SpringBootApplication
// STRICT ISOLATION: Only scan this specific example's package!
@ComponentScan("com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
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