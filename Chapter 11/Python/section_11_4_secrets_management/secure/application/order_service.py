"""
APPLICATION LAYER - Order Service

This is the application service that orchestrates business logic.
It depends on the ISecretsProvider abstraction (Port), not on any
specific secrets manager implementation.
"""

from typing import Tuple
from ..domain.secrets_port import ISecretsProvider, SecretNotFoundError, SecretPermissionError


class OrderService:
    """
    APPLICATION SERVICE: Orchestrates order-related operations.
    
    ARCHITECTURAL NOTE:
    This is the Application Layer in Clean Architecture. It:
    1. Contains business logic
    2. Depends on abstractions (ISecretsProvider), not concretions
    3. Doesn't know HOW secrets are retrieved, only THAT they can be retrieved
    
    SECURITY BENEFITS:
    - No credentials in source code
    - Credentials rotated automatically by AWS
    - Least privilege IAM role
    - Full audit trail in CloudTrail
    """
    
    def __init__(self, secrets_provider: ISecretsProvider):
        """
        Initialize the Order Service with a secrets provider.
        
        Args:
            secrets_provider: Implementation of ISecretsProvider (injected via DI)
        """
        self.secrets_provider = secrets_provider
    
    def connect_to_database(self, secret_name: str) -> Tuple[bool, str]:
        """
        Connect to the database using credentials from the secrets manager.
        
        Args:
            secret_name: The name of the secret in AWS Secrets Manager
            
        Returns:
            Tuple of (success: bool, message: str)
        """
        try:
            # Retrieve credentials from the secrets manager
            # The application code only contains the SECRET NAME, not the secret itself!
            print(f"  [OrderService] Requesting credentials from secrets manager...")
            print(f"  [OrderService] Secret name: {secret_name}")
            
            username, password = self.secrets_provider.get_database_credentials(secret_name)
            
            print(f"  [OrderService] Credentials retrieved successfully")
            print(f"  [OrderService] Connecting to database with username: {username}")
            
            # In a real application, we would now use these credentials to connect
            # For demo purposes, we'll simulate a successful connection
            # conn = psycopg2.connect(
            #     host="prod-db.internal.castle.com",
            #     database="orders_db",
            #     user=username,
            #     password=password
            # )
            
            return True, f"Successfully connected to database as {username}"
            
        except SecretNotFoundError as e:
            error_msg = f"Secret not found: {e}"
            print(f"  [OrderService] ERROR: {error_msg}")
            return False, error_msg
            
        except SecretPermissionError as e:
            error_msg = f"Permission denied: {e}"
            print(f"  [OrderService] ERROR: {error_msg}")
            return False, error_msg
            
        except Exception as e:
            error_msg = f"Unexpected error: {e}"
            print(f"  [OrderService] ERROR: {error_msg}")
            return False, error_msg
    
    def process_order(self, order_id: str, secret_name: str) -> str:
        """
        Process an order by connecting to the database and updating the order.
        
        Args:
            order_id: The order to process
            secret_name: The secret name for database credentials
            
        Returns:
            Status message
        """
        print(f"\n  [OrderService] Processing order {order_id}...")
        
        # Connect to database using secure credentials
        success, message = self.connect_to_database(secret_name)
        
        if not success:
            return f"Failed to process order {order_id}: {message}"
        
        # Simulate order processing
        print(f"  [OrderService] Updating order status to 'PROCESSED'...")
        print(f"  [OrderService] Order {order_id} processed successfully!")
        
        return f"Order {order_id} processed successfully"