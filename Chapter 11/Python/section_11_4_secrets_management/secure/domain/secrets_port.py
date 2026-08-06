"""
DOMAIN LAYER - Secrets Port (Interface)

This defines the contract for retrieving secrets. The application layer
depends on this abstraction, not on any specific secrets manager implementation.
"""

from abc import ABC, abstractmethod
from typing import Tuple


class ISecretsProvider(ABC):
    """
    PORT: Abstract interface for secrets management.
    
    ARCHITECTURAL NOTE:
    This is a Port in Hexagonal Architecture. The application core defines
    WHAT it needs (an interface), and the infrastructure layer provides
    HOW it's implemented (AWS Secrets Manager, HashiCorp Vault, etc.).
    
    This allows us to:
    1. Swap secrets providers without changing business logic
    2. Test with mock implementations
    3. Support multiple cloud providers
    """
    
    @abstractmethod
    def get_database_credentials(self, secret_name: str) -> Tuple[str, str]:
        """
        Retrieve database credentials from the secrets manager.
        
        Args:
            secret_name: The name/ARN of the secret to retrieve
            
        Returns:
            Tuple of (username, password)
            
        Raises:
            SecretNotFoundError: If the secret doesn't exist
            SecretPermissionError: If the IAM role lacks permission to access the secret
        """
        pass


class SecretNotFoundError(Exception):
    """Raised when a requested secret cannot be found."""
    pass


class SecretPermissionError(Exception):
    """Raised when the application lacks IAM permission to access a secret."""
    pass
