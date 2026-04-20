from rx import operators as ops
import json

class AwsLambdaHandler:
    """
    CLOUD 1: AWS LAMBDA (The Imperative Island - Reactive)
    
    THE ARCHITECTURAL LESSON: 
    Even when using modern Observables, AWS remains "Infrastructure-Heavy." 
    Because the platform only hands you metadata, your reactive pipeline 
    must contain the "Plumbing" to go fetch the actual data bytes.
    
    TEACHING NOTE:
    Notice how the 'ops.map' operator is used to navigate the proprietary 
    AWS S3 dictionary. This represents an "Abstraction Leak"—the vendor's 
    data model is now physically part of our reactive logic.
    """
    
    def handle_stream(self, event_stream_obs, context):
        """
        Observes a stream of AWS S3 events and projects a response.
        """
        return event_stream_obs.pipe(
            ops.map(lambda event: self._process_logic(event, context))
        )

    def _process_logic(self, event, context):
        # 1. THE CLOUD CONTRACT: Navigating proprietary JSON
        bucket_name = event['Records'][0]['s3']['bucket']['name']
        file_name = event['Records'][0]['s3']['object']['key']

        print(f"      [AWS Lambda] Request {context.aws_request_id} observed: {file_name}")
        
        # 2. THE IMPERATIVE FETCH: We are responsible for the network call
        print(f"      [AWS Lambda] Plumbing: Manually fetching bytes via Boto3 SDK...")
        
        # 3. THE LOGIC:
        print(f"      [AWS Lambda] Processing image resize reactively...")
        
        # 4. THE RESPONSE: AWS-compliant return structure
        return {
            'statusCode': 200,
            'body': json.dumps(f"AWS reactive processed {file_name}")
        }