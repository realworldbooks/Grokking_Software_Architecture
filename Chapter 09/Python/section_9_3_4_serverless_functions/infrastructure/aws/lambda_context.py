class MockLambdaContext:
    """
    THE AWS RUNTIME CONTEXT:
    
    TEACHING NOTE:
    This simulates the 'context' object provided by the AWS Lambda Runtime. 
    It exposes infrastructure-level metadata like the AWS Request ID. 
    It represents the "Physical Reality" of the hosting environment that 
    the cloud vendor injects into your execution thread.
    """
    def __init__(self):
        self.aws_request_id = "aws-req-rx-12345"
        self.function_name = "image-resizer-reactive"
        self.memory_limit_in_mb = 128