# Chapter 9: Architecting for the Cloud

Welcome to the companion code for Chapter 9. While previous chapters focused on application logic, boundaries, and databases, Chapter 9 explores the physical reality of where that code runs. 

The cloud isn't magic; it's just someone else's computer. In this chapter, we bridge the gap between software engineering and cloud infrastructure. We are moving away from manual, click-driven server configuration and stepping into the automated, declarative world of the Cloud. You will explore how to architect systems that treat infrastructure as an extension of the codebase itself.

⚠️ **IMPORTANT NOTE ON TERRAFORM FILES:** Unlike the application code in previous chapters, the `.tf` and `.md` files located in the `terraform_walkthrough` directory are **not designed to be executed**. They are *Architectural Walk-Throughs*. Running `terraform apply` requires active AWS accounts, billing configurations, and specific IAM roles. Instead, these files are heavily annotated with "Architect Notes" to teach you how to *read and review* Infrastructure as Code (IaC) safely without risking your wallet.

## Architectural Concepts Covered
1. **Pets vs. Cattle (Immutable Infrastructure):**
   - **Pets:** Hand-crafted, manually patched servers that cause panic when they go offline.
   - **Cattle:** Disposable, identical servers spun up automatically from a script. If one gets sick, you terminate it and spin up an identical replacement.
   
2. **Infrastructure as Code (IaC):**
   - We explore **Terraform** as a declarative blueprint. Instead of writing imperative scripts explaining *how* to build a server step-by-step, we declare *what* the final state of the cloud should look like.

3. **The Execution Plan & Blast Radius:**
   - The execution plan is your last line of defense before a cloud deployment. We demonstrate how a Clarity Engineer reads a plan to spot hidden "Destroy-and-Recreate" actions that could wipe out a production database.

## How to Run the Examples

### 1. .NET (C#)
- **Prerequisites:** .NET 8.0 SDK or higher.
- **Tools Used:** - 
  - **System.Reactive (Rx.NET):** Used to simulate asynchronous, event-driven streams (like S3 file uploads) triggering Serverless FaaS functions.
  - **Amazon.Lambda.Events:** Used to enforce real-world cloud contracts, ensuring our local functions match actual AWS S3 and API Gateway event signatures.
- Navigate to the `Chapter09/C#/` directory.
- Run the interactive console master menu:
  ```bash
  dotnet run
  ```

### 2. Java
- **Prerequisites:** Java 17 and Maven.
- **Tools Used:** - 
   - **RxJava3:** Implements the Reactive push-model to simulate cloud event triggers safely in local memory.
   - **AWS Lambda Java Core & Events:** Provides the official AWS interfaces to demonstrate exactly how production Java Serverless functions are structured
- Navigate to the `Chapter09/Java/` directory.
- Compile and execute the interactive master menu:
```bash
mvn clean compile exec:java
```
### 3. Node.js (JavaScript)
- **Prerequisites:** Node.js (v18+) and npm.
- **Tools Used:**
   - **RxJS:** The JavaScript implementation of Reactive Extensions, used here to model the ephemeral, push-based nature of Serverless event streams without needing an active AWS EventBridge setup.
- Navigate to the `Chapter09/JS/` directory.
- Install dependencies, push the schema to the database (generates the Prisma Client), and start the menu:
```bash
npm install
node menu.js
```
(Note: The db:setup command runs prisma db push behind the scenes to build your local SQLite file).

### 4. Python
- **Prerequisites:** Python 3.12. (Note: Python 3.13 introduces typing changes that may break older versions of SQLAlchemy).
- **Tools Used:** 
   To demonstrate these cloud-native concepts locally without requiring you to have an active AWS account, the Python implementation utilizes specific architectural libraries:

   - **boto3 & moto:** boto3 is the official AWS SDK, used to programmatically provision infrastructure. However, we wrap it in moto, a powerful mocking library that intercepts the AWS calls and simulates the cloud environment entirely in your local memory. This allows you to practice declarative deployments safely and cost-free, ensuring no EC2 instances are accidentally left running all weekend!

   - **rx / RxPY:** Serverless architecture (FaaS) relies on an Event-Driven "Push" model. Instead of a server polling a database in an infinite loop, the cloud pushes an event to your function. We use rx (Reactive Extensions) to simulate this asynchronous event stream (e.g., an S3 bucket upload trigger) firing off ephemeral, stateless Lambda functions.

- Navigate to the `Chapter09/Python/` directory.
- Create and activate a virtual environment, install dependencies, and run the menu:
```PowerShell
py -3.12 -m venv .venv
.\.venv\Scripts\activate      # On Windows
# source .venv/bin/activate   # On Mac/Linux

pip install -r requirements.txt
python menu.py
```

## Project Structure
All languages share a unified folder structure. 
The scenarios are split into isolated sections but run from a single Master Menu at the root.

```Plaintext
├── [Root Menu File]                          # The Master CLI Menu (Program.cs, menu.py, menu.js, etc.)
│
├── section_9_2_3_stateful_vs_stateless/      # Demonstrates horizontal scaling limitations
│   ├── Infrastructure/                       # Mock load balancers and server instances
│   ├── Services/                             # The core business logic (Cart/Session management)
│   └── Demo                                  # Execution scenario proving why stateful servers fail to scale
│
├── section_9_3_4_serverless_functions/       # The Event-Driven Push Model (FaaS)
│   ├── Handlers/                             # Ephemeral Lambda/Function logic (no loops, no servers)
│   ├── Infrastructure/                       # Simulated Event Streams (e.g., RxPY, EventEmitters)
│   └── Demo                                  # Execution scenario showing "Scale to Zero" efficiency
│
└── section_9_5_3_infrastructure_review/      # Infrastructure as Code (IaC) Auditing
    ├── execution_plan.md                     # Guided walkthrough of auditing a Terraform Plan
    └── main.tf                               # The declarative blueprint of the cloud environment                           
```
## Feature Comparison Map
| Section | Architectural Goal | The Problem (Before) | The Solution (After) |
| :--- | :--- | :--- | :--- |
| **9.2.3** | **Horizontal Scaling** | **Stateful Servers:** User sessions are stored in local memory. If a load balancer routes a user to a different server, they are suddenly logged out and their cart is empty. | **Stateless Servers:** User sessions are pushed to a centralized, high-speed cache (like Redis). Any server can handle any request, allowing the application to scale infinitely. |
| **9.3.4** | **Compute Efficiency** | **Always-On Servers:** An EC2 instance running 24/7 waiting for image uploads, wasting money and CPU cycles during idle hours. | **Serverless (FaaS):** Code is executed strictly in response to an event (e.g., an S3 upload). The environment scales to zero when idle, saving costs. |
| **9.5.3** | **Deployment Safety** | **Blind Applies:** Running deployment scripts without understanding what the cloud engine intends to change, risking massive data loss. | **Execution Plan Review:** Using `terraform plan` to audit the diff. The architect actively hunts for Red Light (`-`) and Replacement (`-/+`) symbols on stateful resources. |