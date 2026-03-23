# 4.4 Anti-Patterns: Rich Domain & Thin Controller (After Refactoring)
This Java (Spring Boot) project demonstrates the "After" state of a professionally structured Layered Architecture. It resolves the "Fat Controller" and "Anemic Domain" anti-patterns by logically isolating business rules into a Rich Domain Model and coordinating data via a Service Layer (Orchestrator).

## Architectural Highlights
* **Logical Layering, Not Tiers:** This project is structured logically into four distinct layers (Presentation, Application, Infrastructure, and Domain). Because they all run within the same physical JVM process during execution, this is a Layered Architecture rather than an N-Tier architecture.

* **The Composition Root:** The Main.java file, annotated with `@SpringBootApplication`, sits at the very top of the application. It acts as the Composition Root, where Spring's Inversion of Control (IoC) container wires all layers together via Dependency Injection.

* **Targeted Component Scanning:** Because this example shares a single Maven project (`pom.xml`) with the rest of Chapter 4, `Main.java` uses `@ComponentScan` to strictly isolate and load only the classes in the `after_rich_domain_thin_controller` package.

* **Secure Data Lookup (Source of Truth):** The `OrderRequest` DTO only accepts an `itemId` and `quantity`. The `OrderServiceImpl` securely fetches the official item price from the `SqlItemRepository`, preventing clients from manipulating prices via the API.

* **Rich Domain Encapsulation:** The `Order` domain model natively handles its own state, discount calculations, and business logic without leaking it to the Service layer.

## Project Structure
```Plaintext
Chapter04/Java/
├── pom.xml                          (Shared Maven Dependencies & Spring Boot Config)
└── src/main/java/com/grokkingsoftwarearchitecture/chapter04/
    └── section_4_4_anti_patterns/
        └── after_rich_domain_thin_controller/
          ├── application/
            │   ├── OrderService.java          (The Interface Contract)
            │   ├── OrderServiceImpl.java      (The Orchestrator)
            │   └── OrderRequest.java          (The DTOs)
            ├── infrastructure/
            │   ├── CustomerRepository.java    (Data Contracts)
            │   ├── ItemRepository.java        
            │   ├── OrderRepository.java       
            │   ├── EmailService.java          
            │   ├── SqlCustomerRepository.java (Simulated DB Lookups)
            │   ├── SqlItemRepository.java     
            │   ├── SqlOrderRepository.java    
            │   └── SmtpEmailService.java
            ├── domain/
            │   ├── Customer.java
            │   ├── Item.java
            │   └── Order.java                 (The Rich Domain Model)
            └── presentation/
                ├── OrderController.java       (The Thin Controller)
                └── Main.java                  (The Composition Root & Entry Point)
```
## How to Run
Because all of Chapter 4 shares a single `pom.xml`, you need to tell Maven exactly which `Main.java` file to execute so it doesn't try to run the "Before" examples.

**Step 1:** Open your terminal and navigate to the directory containing the `pom.xml`:

```Bash
cd "Chapter 04/Java/"
```
**Step 2:** Run the application using the Spring Boot Maven plugin:

```Bash
mvn spring-boot:run -Dspring-boot.run.main-class=com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.presentation.Main
```
(Alternatively, you can open the project in IntelliJ IDEA, Eclipse, or VS Code, navigate to this specific `Main.java` file, and click the "Run" play button next to the `public static void main` method).
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

* **Swagger UI (Recommended):** This is the easiest way to test your API. Spring Boot automatically generates this UI for us.

1.  After the server is running, open your web browser.
2.  In the address bar, go to:
    **`http://localhost:8080/swagger-ui/index.html`**
3.  You will see the Swagger UI page. Click on the `POST /order` endpoint to expand it.
4.  Click the **"Try it out"** button (on the right).
5.  The "Request body" text box will become editable. Replace the contents with this JSON:
    ```json
    {
      "customerId": 123,
      "items": [
        {
          "itemId": 1,
          "quantity": 1
        },
        {
           "itemId": 2,
           "quantity": 2
        }
      ]
    }
    ```
6.  Click the big blue **"Execute"** button.

* **Standardized Testing:** Use the `.http` file shared across the book's examples. Ensure the `@host` variable at the top of the `.http` file is set to `http://localhost:8080`.

### Expected Result
You will see a "Server response" with a `200` code and a response body showing your new order ID. In your terminal running the Spring Boot app, you will see the logs proving the secure item lookup executed successfully!