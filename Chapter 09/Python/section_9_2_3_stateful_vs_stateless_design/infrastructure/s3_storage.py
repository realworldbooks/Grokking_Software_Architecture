import boto3

class S3Storage:
    """
    THE STATELESS ADAPTER (Cloud Native Design):
    
    TEACHING NOTE:
    This adapter moves the "state" out of the individual web servers and into 
    a centralized, external location (Amazon S3). 
    
    Because our UserService relies on Dependency Injection, we can swap 
    from the fragile LocalStorage to this robust Cloud provider without 
    changing a single line of our business logic!
    """
    
    def __init__(self, bucket_name: str):
        self.bucket_name = bucket_name
        # In real life, boto3 connects to the actual AWS Cloud.
        # In our demo, the @mock_aws decorator will intercept this and run it in memory!
        self.s3_client = boto3.client('s3', region_name='us-east-1')
        self.s3_client.create_bucket(Bucket=self.bucket_name)

    def save(self, file_name: str, data: str) -> None:
        self.s3_client.put_object(Bucket=self.bucket_name, Key=file_name, Body=data)

    def get(self, file_name: str) -> str:
        try:
            response = self.s3_client.get_object(Bucket=self.bucket_name, Key=file_name)
            return response['Body'].read().decode('utf-8')
        except self.s3_client.exceptions.NoSuchKey:
            raise FileNotFoundError(f"404 Not Found in S3 Bucket: {file_name}")