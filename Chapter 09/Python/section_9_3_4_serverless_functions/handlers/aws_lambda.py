import json

def aws_handler(event: dict, context: dict) -> dict:
    """
    CLOUD 1: AWS LAMBDA (The Imperative Island)
    
    THE ARCHITECTURAL LESSON: 
    This code is "Infrastructure-Heavy." AWS gives you the metadata, but it's 
    up to you to write the instructions to get the actual work done.
    
    TEACHING NOTE:
    Notice how tightly this code is tied to AWS. The 'event' object is a 
    proprietary AWS dictionary. To even start resizing an image, we first 
    have to write code to navigate through AWS-specific keys like ['Records'][0]['s3']. 
    This is an "Abstraction Leak"—our code knows way too much about Amazon's 
    internal data structures.
    """
    
    # 1. THE CLOUD CONTRACT: Navigating proprietary JSON
    # This logic only works if the trigger is an AWS S3 event.
    bucket_name = event['Records'][0]['s3']['bucket']['name']
    file_name = event['Records'][0]['s3']['object']['key']

    print(f"      [AWS Lambda] Detected upload in bucket: {bucket_name}")
    
    # 2. THE IMPERATIVE FETCH: We are responsible for the network call
    # If we wanted to test this logic locally, we'd have to mock the Boto3 SDK.
    print(f"      [AWS Lambda] Manually fetching bytes using Boto3 SDK...")
    
    # 3. THE LOGIC: Baked directly into the cloud trigger
    print(f"      [AWS Lambda] Processing image resize...")
    
    # 4. THE RESPONSE: Coupled to AWS API Gateway/Lambda requirements
    return {
        'statusCode': 200,
        'body': json.dumps(f"AWS processed {file_name}")
    }