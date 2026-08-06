"""
INFRASTRUCTURE LAYER - AWS Secrets Manager Adapter

This adapter implements the ISecretsProvider interface using AWS Secrets Manager.
In production, this would connect to the real AWS service. For this demo,
we use moto to mock AWS services so the code is fully runnable without
AWS credentials.
"""

import json
from typing import Tuple
from moto import mock_aws
import boto3
from botocore.exceptions import ClientError

from ..domain.secrets_port import ISecretsProvider, SecretNotFoundError, SecretPermissionError


class AwsSecretsManagerAdapter(ISecretsProvider):
    """
    ADAPTER: AWS Secrets Manager implementation of ISecretsProvider.
    
    ARCHITECTURAL NOTE:
    This is the Infrastructure Adapter that implements the Domain Port.
    It knows HOW to retrieve secrets from AWS, but the application layer
    only knows THAT it can retrieve secrets (via the interface).
    
    SECURITY FEATURES:
    1. No hardcoded credentials - uses IAM role from compute platform
    2. Short-lived credentials automatically rotated by AWS
    3. Least privilege - only reads the specific secret needed
    4. Audit trail - all access logged in CloudTrail
    """
    
    def __init__(self, region_name: str = "us-east-1"):
        """
        Initialize the AWS Secrets Manager client.
        
        NOTE: In production, boto3 automatically uses the IAM role
        assigned to the compute platform (EC2, ECS, Lambda, etc.).
        No explicit credentials needed!
        """
        self.region_name = region_name
        self._client = None
    
    def _get_client(self):
        """Lazy initialization of the boto3 client."""
        if self._client is None:
            self._client = boto3.client(
                service_name='secretsmanager',
                region_name=self.region_name
            )
        return self._client
    
    @mock_aws
    def get_database_credentials(self, secret_name: str) -> Tuple[str, str]:
        """
        Retrieve database credentials from AWS Secrets Manager.
        
        Args:
            secret_name: The name/ARN of the secret (e.g., "prod/BillingDatabase/Credentials")
            
        Returns:
            Tuple of (username, password)
            
        Raises:
            SecretNotFoundError: If the secret doesn't exist
            SecretPermissionError: If the IAM role lacks permission
        """
        client = self._get_client()
        
        try:
            # Request the secret from AWS Secrets Manager
            get_secret_value_response = client.get_secret_value(
                SecretId=secret_name
            )
            
            # Parse the JSON response
            secret_string = get_secret_value_response['SecretString']
            secret_dict = json.loads(secret_string)
            
            # Return credentials
            username = secret_dict['username']
            password = secret_dict['password']
            
            return username, password
            
        except ClientError as e:
            error_code = e.response['Error']['Code']
            
            if error_code == 'ResourceNotFoundException':
                raise SecretNotFoundError(f"Secret '{secret_name}' not found")
            elif error_code == 'AccessDeniedException':
                raise SecretPermissionError(
                    f"IAM role lacks permission to access secret '{secret_name}'. "
                    f"Grant 'secretsmanager:GetSecretValue' permission."
                )
            else:
                # Re-raise other AWS errors
                raise


class MockSecretsProviderForTesting(ISecretsProvider):
    """
    MOCK IMPLEMENTATION: For unit testing without AWS.
    
    This allows developers to test their code without needing AWS credentials
    or the moto library. In production, this would be replaced with the
    real AWSSecretsManagerAdapter.
    """
    
    def __init__(self, mock_secrets: dict):
        """
        Initialize with a dictionary of mock secrets.
        
        Args:
            mock_secrets: Dict mapping secret_name -> (username, password)
        """
        self.mock_secrets = mock_secrets
    
    def get_database_credentials(self, secret_name: str) -> Tuple[str, str]:
        """Retrieve from mock storage."""
        if secret_name not in self.mock_secrets:
            raise SecretNotFoundError(f"Mock secret '{secret_name}' not found")
        
        return self.mock_secrets[secret_name]