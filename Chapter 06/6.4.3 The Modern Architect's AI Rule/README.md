# AI API Prompts: Cross-Language Examples

The architectural principle taught in Chapter 6, **"Your API Schema is the System Prompt"**, is universal. While the book focuses on C# and .NET, this folder demonstrates how to apply the exact same "Code-as-Prompt" architecture in Python, Java, and Node.js.

## Why are there dependencies here?
The main C# repository for this book strictly avoids third-party packages. This is possible because Microsoft includes OpenAPI (Swagger) generation directly in the standard .NET SDK. 

However, in ecosystems like Python, Node.js, and Java, the standard HTTP libraries do not know how to parse code comments (`/** ... */` or docstrings) into an OpenAPI specification. To avoid writing massive, unmaintainable JSON files by hand, we must use the industry-standard frameworks for each language to automatically generate AI prompts.

### 🐍 Python (FastAPI)
FastAPI automatically translates Python docstrings and Pydantic models into OpenAPI specs.
* **Setup:** `pip install -r requirements.txt`
* **Run:** `uvicorn main:app --reload`
* **View the AI UI:** `http://localhost:8000/docs`

### ☕ Java (Spring Boot)
Spring Boot uses the `springdoc-openapi` package to read `@Operation` and `@Schema` annotations.
* **Run:** `mvn spring-boot:run`
* **View the AI UI:** `http://localhost:8080/swagger-ui.html`

### 🌐 Node.js (Express)
Express uses `swagger-jsdoc` to parse YAML-style JSDoc comments placed directly above your routes.
* **Setup:** `npm install`
* **Run:** `npm start`
* **View the AI UI:** `http://localhost:3000/api-docs`
