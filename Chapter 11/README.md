# Chapter 11: Secrets Management

Welcome to the companion code for Chapter 11. In the early days of software, developers would hardcode database credentials directly into configuration files (such as `appsettings.json` or `.env`) and commit those files to the source code repository. This is an architectural disaster. Anyone with read access to the codebase—whether a junior developer, a compromised CI/CD pipeline, or a malicious insider—instantly gains the keys to the kingdom.

Chapter 11 focuses on removing the human and the hardcoded string from the equation. Modern architects must refactor applications to request credentials from a **Secrets Manager** (such as AWS Secrets Manager, HashiCorp Vault, or Azure Key Vault) at runtime using a short-lived IAM role assigned to the server. These examples demonstrate both the "Snowflake Server" anti-pattern that has caused countless security breaches, and the secure, cloud-native architecture that modern applications must adopt.

## Architectural Concepts Covered
1. **The Hardcoded Disaster (Anti-Pattern):**
   - **Source Control Leakage:** Once you commit credentials to GitHub or GitLab, they are compromised forever. Even if you delete them later, they remain in the git history.
   - **The "God Account" Problem:** Using the `admin` user violates the Principle of Least Privilege. If the service is breached, the attacker can drop every table in the database.
   - **Static Vulnerability:** If you need to change the password, you have to recompile, re-test, and re-deploy the entire application.

2. **Secure Runtime Retrieval (The Right Way):**
   - **Zero Credentials in Source Code:** The application code contains only the **secret name**, never the actual credentials. The secret name is not sensitive—only the value is.
   - **IAM Role Authentication:** The compute platform (EC2, ECS, Lambda) assumes a short-lived, temporary IAM role that is automatically rotated by the cloud provider. We do not use permanent, long-lived access keys.
   - **Least Privilege:** The IAM role is granted only the permission necessary to read that specific secret. Humans and permanent credentials are entirely removed from the loop.
   - **Automatic Rotation:** AWS rotates credentials automatically without code deployment. Short-lived credentials reduce the blast radius if compromised.
   - **Full Audit Trail:** All secret access is logged in CloudTrail for compliance and security monitoring.

3. **Hexagonal Architecture (Ports & Adapters):**
   - **Domain Port:** The application layer defines an abstract interface (`ISecretsProvider` in C#, `SecretsProvider` in Java, `ISecretsProvider` in Python, `SecretsPort` in JS) for retrieving secrets—it doesn't care *which* secrets manager is used.
   - **Infrastructure Adapter:** The implementation layer knows *how* to talk to AWS Secrets Manager, HashiCorp Vault, or any other provider.
   - **Dependency Injection:** The application service receives its secrets provider via constructor injection, allowing providers to be swapped without changing business logic.

## How to Run the Examples

### 1. .NET (C#)
- **Prerequisites:** .NET 8.0 SDK or higher.
- **Tools Used:**
  - **AWSSDK.SecretsManager:** The official AWS SDK for .NET, used to interact with AWS Secrets Manager.
  - **System.Text.Json:** The built-in .NET JSON framework used for parsing secret payloads.
- Navigate to the `Chapter11/C#/` directory.
- Run the interactive console master menu:
  ```bash
  dotnet run
  ```

### 2. Java
- **Prerequisites:** Java 17 and Maven.
- **Tools Used:**
  - **AWS SDK for Java v2 (secretsmanager):** The official AWS SDK for Java, used to retrieve secrets at runtime via IAM role.
  - **Jackson Databind:** A fast JSON parser for Java used for parsing the secret payload response.
- Navigate to the `Chapter11/Java/` directory.
- Compile and execute the interactive master menu:
  ```bash
  mvn clean compile exec:java
  ```

### 3. Node.js (JavaScript)
- **Prerequisites:** Node.js (v18+) and npm.
- **Tools Used:**
  - **@aws-sdk/client-secrets-manager:** The official AWS SDK for JavaScript (v3), used to retrieve secrets at runtime via IAM role.
- Navigate to the `Chapter11/JS/` directory.
- Install dependencies and start the menu:
  ```bash
  npm install
  node menu.js
  ```

### 4. Python
- **Prerequisites:** Python 3.12+.
- **Tools Used:** To demonstrate these secrets management concepts safely without needing real AWS credentials, the Python implementation utilizes specific architectural libraries:
  - **boto3:** The AWS SDK for Python, used to interact with AWS Secrets Manager.
  - **moto:** A library that allows your tests to easily mock out AWS Services, so the examples are fully runnable without an AWS account.
  - **psycopg2-binary:** The PostgreSQL adapter for Python, used to demonstrate the database connection pattern.
- Navigate to the `Chapter11/Python/` directory.
- Create and activate a virtual environment, install dependencies, and run the menu:
  ```bash
  py -m venv .venv
  .\.venv\Scripts\activate      # On Windows
  # source .venv/bin/activate   # On Mac/Linux

  pip install -r requirements.txt
  python menu.py
  ```

## Project Structure
All languages share a unified folder structure. The scenarios are split into the "Before" (Hardcoded Disaster) and "After" (Secure Runtime Retrieval) architectures, but run from a single Master Menu at the root.

```text
├── [Root Menu File]                          # The Master CLI Menu (Program.cs, menu.py, menu.js, Main.java, etc.)
│
└── section_11_4_secrets_management/          # The Secrets Management implementation
    ├── hardcoded/                            # The Hardcoded Disaster (What NOT to do)
    │   ├── Demo                              # Execution scenario showing the security vulnerabilities
    │   └── MockDatabaseConnection            # Simulated DB with hardcoded admin credentials
    │
    └── secure/                               # Secure Runtime Retrieval (The Right Way)
        ├── Demo                              # Execution scenario showing secure credential retrieval
        ├── Core/                             # Domain & Application layers (framework-agnostic)
        │   ├── Domain/                       # Business entities
        │   ├── Ports/                        # Secrets Provider interface (the Port)
        │   ├── Application/                  # OrderService (business logic with DI)
        │   └── Infrastructure/               # Concrete adapters (AWS Secrets Manager implementation)
        └── Infrastructure/
            └── AwsSecretsManagerAdapter      # Adapter implementing the Port
```

## Feature Comparison Map
| Section | Architectural Goal | The Problem (Before) | The Solution (After) |
| :--- | :--- | :--- | :--- |
| **11.4.1** | **Security** | **Source Control Leakage:** Credentials are committed to git history forever. Anyone with repo access (random users, contractors, hackers) has the keys to the database. | **Zero Secrets in Code:** The application code contains only the secret *name*, never the actual credentials. Git repository is clean and safe to share. |
| **11.4.1** | **Access Control** | **The "God Account" Problem:** Using the `admin` user violates the Principle of Least Privilege. If this service is breached, the attacker can drop every table in the database. | **IAM Role Authentication:** The compute platform assumes a short-lived IAM role with Least Privilege, granting access only to the specific secret the service needs. |
| **11.4.1** | **Operations** | **Static Vulnerability:** Changing the password requires recompiling, re-testing, and re-deploying the entire application. | **Automatic Rotation:** AWS rotates credentials automatically without code deployment, and short-lived credentials reduce the blast radius if compromised. |