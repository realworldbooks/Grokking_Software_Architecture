import json
from moto import mock_aws
import boto3

from .application.order_service import OrderService
from .infrastructure.aws_secrets_adapter import AwsSecretsManagerAdapter


class Demo:
    """
    SECURE RUNTIME RETRIEVAL - Listing 11.2
    
    DESIGN NOTE:
    This example demonstrates the RIGHT way to handle secrets. We use AWS Secrets Manager
    (mocked with moto) to retrieve credentials at runtime. The application code contains
    ONLY the secret name, never the actual credentials.
    
    ARCHITECTURAL BENEFITS:
    1. ZERO CREDENTIALS IN SOURCE CODE: The secret name is not sensitive, only the value is.
    2. IAM ROLE AUTHENTICATION: The compute platform's IAM role proves identity, no passwords needed.
    3. AUTOMATIC ROTATION: AWS rotates credentials automatically without code deployment.
    4. LEAST PRIVILEGE: The IAM role can only read its specific secret.
    5. FULL AUDIT TRAIL: All access logged in CloudTrail for compliance.
    """

    @staticmethod
    def run():
        print("\n=== Section 11.4.2: Secure Runtime Retrieval (Python) ===")
        print("THE SETUP: An Order Service that needs database credentials.")
        print("THE SOLUTION: Credentials retrieved from AWS Secrets Manager at runtime.\n")

        # ---------------------------------------------------------
        # SETUP: Create a mock secret in AWS Secrets Manager
        # ---------------------------------------------------------
        print("--- SETUP: Initializing AWS Secrets Manager (mocked) ---")
        
        with mock_aws():
            # Create the Secrets Manager client
            secrets_client = boto3.client('secretsmanager', region_name='us-east-1')
            
            # Create a secret with database credentials
            secret_name = "prod/BillingDatabase/Credentials"
            secret_value = json.dumps({
                "username": "order_service_user",
                "password": "SecureRandomPassword123!@#",
                "host": "prod-db.internal.castle.com",
                "database": "orders_db"
            })
            
            # Store the secret in AWS Secrets Manager
            secrets_client.create_secret(
                Name=secret_name,
                SecretString=secret_value,
                Description="Database credentials for Order Service"
            )
            
            print(f"  [AWS Secrets Manager] Created secret: {secret_name}")
            print(f"  [AWS Secrets Manager] Secret contains: username, password, host, database")
            print(f"  [AWS Secrets Manager] IAM Role: order-service-role (Least Privilege)\n")

            # ---------------------------------------------------------
            # THE SECURE ARCHITECTURE IN ACTION
            # ---------------------------------------------------------
            print("--- SCENARIO: Order Service starts and needs database credentials ---")
            print("  [EC2/ECS/Lambda] Instance starts with IAM role 'order-service-role'")
            print("  [OrderService] Application code contains ONLY the secret name")
            print(f"  [OrderService] Secret name: {secret_name}")
            print("  [OrderService] No passwords, no credentials in source code!\n")

            # Create the secrets adapter (Infrastructure layer)
            # In production, this would use the real AWS IAM role
            secrets_adapter = AwsSecretsManagerAdapter(region_name="us-east-1")
            
            # Create the Order Service with Dependency Injection
            # The service doesn't know it's using AWS - it just knows it has a secrets provider
            order_service = OrderService(secrets_provider=secrets_adapter)

            # ---------------------------------------------------------
            # EXECUTION: Process an order with secure credentials
            # ---------------------------------------------------------
            print("--- EXECUTING: Processing order with secure credential retrieval ---\n")
            
            # Process an order - credentials are fetched at runtime
            result = order_service.process_order(
                order_id="ORD-SECURE-001",
                secret_name=secret_name
            )
            
            print(f"\n  [Result] {result}")

            # ---------------------------------------------------------
            # DEMONSTRATE: What happens with wrong secret name
            # ---------------------------------------------------------
            print("\n--- SCENARIO: Attempting to access non-existent secret ---")
            print("  [OrderService] Trying to access wrong secret name...")
            
            result2 = order_service.process_order(
                order_id="ORD-SECURE-002",
                secret_name="prod/NonExistent/Credentials"
            )
            
            print(f"\n  [Result] {result2}")

            # ---------------------------------------------------------
            # THE ARCHITECTURAL VERDICT
            # ---------------------------------------------------------
            print("\n" + "=" * 70)
            print("ARCHITECTURAL VERDICT: ZERO-TRUST SECURITY ARCHITECTURE")
            print("-" * 70)
            print("SECURITY #1: NO CREDENTIALS IN SOURCE CODE")
            print("  - Application code contains only the secret NAME (not sensitive)")
            print("  - Actual credentials stored securely in AWS Secrets Manager")
            print("  - Git repository is clean - no secrets to leak")
            print()
            print("SECURITY #2: IAM ROLE AUTHENTICATION")
            print("  - Compute platform (EC2/ECS/Lambda) has IAM role assigned")
            print("  - Application proves identity via IAM role, not passwords")
            print("  - No permanent access keys needed")
            print()
            print("SECURITY #3: AUTOMATIC CREDENTIAL ROTATION")
            print("  - AWS rotates credentials automatically (no code deployment)")
            print("  - Short-lived credentials reduce blast radius if compromised")
            print("  - Rotation schedule configurable per secret")
            print()
            print("SECURITY #4: LEAST PRIVILEGE ACCESS")
            print("  - IAM role can only read its specific secret")
            print("  - Cannot access other secrets or AWS resources")
            print("  - Principle of Least Privilege enforced by IAM policies")
            print()
            print("SECURITY #5: FULL AUDIT TRAIL")
            print("  - All secret access logged in AWS CloudTrail")
            print("  - Compliance reporting available")
            print("  - Can detect and alert on suspicious access patterns")
            print()
            print("OPERATIONAL BENEFITS:")
            print("  - Rotate passwords without downtime or deployment")
            print("  - Different credentials per environment (dev/staging/prod)")
            print("  - Centralized secrets management across all services")
            print("  - Integration with AWS IAM for fine-grained access control")
            print()
            print("REALITY CHECK: This is how modern cloud-native applications")
            print("handle secrets. The application never sees the actual password,")
            print("only the secret name. The cloud provider handles authentication,")
            print("rotation, and audit logging. Humans and permanent credentials")
            print("are completely removed from the equation.")
            print("=" * 70 + "\n")