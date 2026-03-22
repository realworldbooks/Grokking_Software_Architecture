# 4.4 Anti-Patterns: Rich Domain & Thin Controller (After Refactoring)

This Python project demonstrates the "After" state of a professionally structured N-Tier architecture. It resolves the "Fat Controller" and "Anemic Domain" anti-patterns by isolating business rules into a **Rich Domain Model** and coordinating data via a **Service Layer** (Orchestrator).

## Architectural Highlights

* **No Framework Magic Needed**: Unlike C# (`.csproj`) or Java (`pom.xml`), this pure Python implementation requires no heavy configuration files to demonstrate N-Tier separation.
* **The Composition Root**: The `presentation/main.py` file sits at the very top of the architecture. It is responsible for wiring all layers together via Dependency Injection before executing the application.
* **Secure Data Lookup (Source of Truth)**: The `OrderRequest` DTO now only accepts an `item_id` and `quantity`. The `OrderService` securely fetches the official item price from the `SqlItemRepository`, preventing clients from manipulating prices via the API.
* **Rich Domain Encapsulation**: The `Order` domain model natively handles its own state, discount calculations, and business logic without leaking it to the Service layer.

## Project Structure

```text
after_rich_domain_thin_controller/
├── business_logic/
│   ├── i_order_service.py       (The Interface Contract)
│   ├── order_request.py         (The DTOs)
│   └── order_service.py         (The Orchestrator)
├── data_access/
│   ├── data_access_interfaces.py 
│   ├── email_service.py
│   └── repositories.py          (Simulated DB Lookups)
├── domain_models/
│   ├── customer.py
│   ├── item.py
│   └── order.py                 (The Rich Domain Model)
└── presentation/
    ├── controllers/
    │   └── order_controller.py  (The Thin Controller)
    └── main.py                  (The Composition Root & Entry Point)
```
How to Run
Because this project enforces strict architectural boundaries using Python's relative imports (e.g., from ..business_logic...), you cannot run the main script directly from inside the presentation folder. Doing so will result in an ImportError.

To run the application correctly, you must execute it as a module from the root After-RichDomain directory.

Step 1: Open your terminal and navigate to the root of this specific example:
```bash
cd "Chapter 04/Python/section_4_4_anti_patterns/after_rich_domain_thin_controller"
```
Step 2: Run the application using the -m (module) flag:

```bash
python -m presentation.main
```
Expected Output
When executed successfully, you will see the Composition Root wire the dependencies, followed by the simulated database lookups and the final successful HTTP 200 response:
```Plaintext
--- Running Traditional 4-Layer Architecture ---
Fat Controller and Anemic Domain eliminated.
  [DB] Fetching official data for Item ID: 1 from SQL.
  [DB] Fetching official data for Item ID: 2 from SQL.
HTTP 200 OK: 1
```
