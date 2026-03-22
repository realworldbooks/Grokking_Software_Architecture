# 4.4 Anti-Patterns: Rich Domain & Thin Controller (After Refactoring)
This Java (Spring Boot) project demonstrates the "After" state of a professionally structured Layered Architecture. It resolves the "Fat Controller" and "Anemic Domain" anti-patterns by logically isolating business rules into a Rich Domain Model and coordinating data via a Service Layer (Orchestrator).

## Architectural Highlights
* **Logical Layering, Not Tiers:** This project is structured logically into four distinct layers (Presentation, Business Logic, Data Access, and Domain). Because they all run within the same physical JVM process during execution, this is a Layered Architecture rather than an N-Tier architecture.

* **The Composition Root:** The Main.java (or Application.java) file, annotated with @SpringBootApplication, sits at the very top of the application. It acts as the Composition Root, where Spring's Inversion of Control (IoC) container wires all layers together via Dependency Injection.
* **Targeted Component Scanning:** Because this example shares a single Maven project (pom.xml) with the rest of Chapter 4, Main.java uses @ComponentScan to strictly isolate and load only the classes in the after_rich_domain_thin_controller package.

* **Secure Data Lookup (Source of Truth):** The OrderRequest DTO now only accepts an itemId and quantity. The OrderServiceImpl securely fetches the official item price from the SqlItemRepository, preventing clients from manipulating prices via the API.

* **Rich Domain Encapsulation:** The Order domain model natively handles its own state, discount calculations, and business logic without leaking it to the Service layer.

## Project Structure
```Plaintext
after_rich_domain_thin_controller/
├── src/main/java/com/grokkingsoftwarearchitecture/chapter04/
│   ├── business_logic/
│   │   ├── OrderService.java        (The Interface Contract)
│   │   ├── OrderServiceImpl.java    (The Orchestrator)
│   │   └── OrderRequest.java        (The DTOs)
│   ├── data_access/
│   │   ├── CustomerRepository.java    (Data Contracts)
│   │   ├── ItemRepository.java        
│   │   ├── OrderRepository.java       
│   │   ├── EmailService.java          
│   │   ├── SqlCustomerRepository.java (Simulated DB Lookups)
│   │   ├── SqlItemRepository.java     
│   │   ├── SqlOrderRepository.java    
│   │   └── SmtpEmailService.java
│   ├── domain_models/
│   │   ├── Customer.java
│   │   ├── Item.java
│   │   └── Order.java               (The Rich Domain Model)
│   └── presentation/
│       ├── OrderController.java     (The Thin Controller)
│       └── Application.java         (The Composition Root & Entry Point)
└── pom.xml                          (Maven Dependencies & Spring Boot Config)
```
## How to Run
This project uses Maven and Spring Boot to provide an enterprise-grade web server and an automated Swagger UI, identical to the C# experience.

**Step 1:** Open your terminal and navigate to the root of this specific example (where the pom.xml is located):

```Bash
cd "Chapter 04/Java/section_4_4_anti_patterns/after_rich_domain_thin_controller"
```
**Step 2:** Run the application using the Spring Boot Maven plugin:

```Bash
mvn spring-boot:run -Dspring-boot.run.main-class=com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation.Application
```
(Alternatively, you can open the project in IntelliJ IDEA, Eclipse, or VS Code and click "Run" on the Application.java file).

## Expected Output & Testing
When executed successfully, you will see the Spring Boot banner and logs confirming the server has started on port 8080:

```Plaintext
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

--- Running Traditional 4-Layer Architecture ---
Fat Controller and Anemic Domain eliminated.
Tomcat initialized with port 8080 (http)
Started Application in 2.145 seconds
```
### To test the API, you have two options:

* **Interactive UI:** Open your browser to http://localhost:8080/swagger-ui/index.html to view and interact with the automatically generated OpenAPI documentation.

* **Standardized Testing:** Use the .http file shared across the book's examples. Ensure the @host variable at the top of the .http file is set to http://localhost:8080.
