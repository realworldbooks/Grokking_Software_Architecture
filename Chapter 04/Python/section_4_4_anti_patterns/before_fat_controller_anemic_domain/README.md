# Grokking Software Architecture: The Fat Controller (Python/FastAPI)

Welcome to the **Before** state of Chapter 4. This project demonstrates the "Fat Controller" anti-pattern—a ticking time bomb of technical debt where a single API route handler assumes responsibility for validation, business logic, data access, and external infrastructure.

**⚠️ WARNING:** This code is intentionally designed with major architectural flaws to illustrate what *not* to do. It produces the correct output, proving that bad architecture can often look perfectly fine from the outside.

## 🚀 Getting Started

This project is built using Python and **FastAPI**. 

### Prerequisites
* **Python 3.12 is highly recommended.** While the code may work with Python 3.8+, using 3.12 streamlines package installation, as pre-compiled binaries are readily available, avoiding the need for a local C++ compiler setup.

### Installation & Execution

1. **Navigate to the project directory:**
   ```bash
   cd chapter-04/python/before_fat_controller
   ```
2. Install the dependencies:
(It is recommended to use a virtual environment)

```Bash
pip install -r requirements.txt
```

3. Run the application:

```Bash
python main.py

(Alternatively, you can run: uvicorn main:app --port 5000)
```

4. Open the Swagger UI:
Navigate your browser to http://localhost:5000/. FastAPI automatically generates this interactive UI.

5. Testing the Anti-Pattern
When you open the Swagger UI, the POST /api/Order endpoint will be pre-filled with the following JSON:

```JSON
{
  "customerId": 1,
  "items": [
    {
      "itemId": 1,
      "quantity": 3
    }
  ]
}
```
- Click Execute. You will receive an HTTP 200 response with a total price of $270.00. The output is identical to the clean, refactored "After" architecture—but the internal code is a structural disaster.

# Architectural Anti-Patterns Demonstrated
If you look inside order_controller.py, you will see several indefensible design decisions:

- Single Responsibility Principle (SRP) Violation: The create_order function does five different jobs. It handles HTTP routing, business math, database queries, and sending emails.

- Tight Infrastructure Coupling: The controller directly instantiates MyDbContext() and SmtpEmailService(). This makes it impossible to swap out the database or email provider without modifying the core business logic.

- Leaked Data Access: The controller acts as a repository, performing messy inline lookups directly against the database collection instead of delegating that responsibility to a data access layer.

- The Anemic Domain Model: If you look at models.py, the Order class has no methods. It is a "dumb" data bag with public properties that the controller manually stuffs data into. The domain cannot protect its own state.

- Zero Testability: You cannot write a unit test for the 10% "Gold Customer" discount without also triggering a fake database connection and printing a fake email to the console.

## Next Steps
After exploring this mess of architecture, move on to the After project to see how we apply the 5-Step Architectural Thinking Process to untangle this mess using a Rich Domain Model and Thin Controller.