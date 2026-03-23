# 4.4 Anti-Patterns: Rich Domain & Thin Controller (After Refactoring)
This `JavaScript (Node.js)` project demonstrates the "After" state of a professionally structured Layered Architecture. It resolves the "Fat Controller" and "Anemic Domain" anti-patterns by logically isolating business rules into a Rich Domain Model and coordinating data via a Service Layer (Orchestrator).

## Architectural Highlights
* **Logical Layering, Not Tiers:** This project is structured logically into four distinct layers (Presentation, Business Logic, Data Access, and Domain). Because they all run within the same physical `Node.js` process during execution, this is a Layered Architecture rather than an N-Tier architecture.

* **The Composition Root:** The `presentation/index.js` file sits at the very top of the application. It is responsible for configuring the Express web server and wiring all layers together via Dependency Injection before executing the program.

* **Secure Data Lookup (Source of Truth):** The `OrderRequest` DTO only accepts an `itemId` and `quantity`. The `OrderService` securely fetches the official item price from the `SqlItemRepository`, preventing clients from manipulating prices via the API.

* **Rich Domain Encapsulation:** The `Order` domain model natively handles its own state, discount calculations, and business logic without leaking it to the Service layer.

## Project Structure
```Plaintext
after_rich_domain_thin_controller/
├── application/
│   ├── orderRequest.js      (The API Contract / DTOs)
│   └── orderService.js      (The Orchestrator)
├── infrastructure/
|   ├── dataAccessInterfaces.js (Simulated Interfaces)
│   ├── emailService.js
│   └── repositories.js      (Simulated DB Lookups)
├── domain/
│   ├── customer.js
│   ├── item.js
│   └── order.js             (The Rich Domain Model)
├──presentation/
│   ├── controllers/
│   │   └── orderController.js   (The Pure JS Thin Controller)
│   └── index.js             (The Composition Root & Express Setup)
└── package.json             (NPM Dependencies)
```
## How to Run
Because this project utilizes `Express.js` and `Swagger UI` to demonstrate a professional Web API setup, you need to install the `Node.js` dependencies before running it.

**Step 1:** Open your terminal and navigate to the root of this specific example:

```Bash
cd "Chapter 04/JavaScript/section_4_4_anti_patterns/after_rich_domain_thin_controller"
```
**Step 2:** Install the required packages:

``Bash
npm install
```
**Step 3:** Start the application:

```Bash
npm start
```
(Note: This runs the `node presentation/index.js` command defined in the package.json)

## Expected Output & Testing
When executed successfully, you will see the console output confirming the server has started:

```Plaintext
--- Running Traditional 4-Layer Architecture ---
Fat Controller and Anemic Domain eliminated.
API listening on port 3000
Swagger UI available at http://localhost:3000/swagger
```
### To test the API, you have two options:

* **Swagger UI (Recommended):** This is the easiest way to test your API.

1.  After the server is running, open your web browser.
2.  In the address bar, go to:
    **`http://localhost:3000/swagger`**
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

* **Standardized Testing:** Use the `.http` file shared across the book's examples. Ensure the `@host` variable at the top of the `.http` file is set to `http://localhost:3000`.

### Expected Result
You will see a "Server response" with a `200` code and a response body showing your new order ID (e.g., `{"orderId": 8264}`).
