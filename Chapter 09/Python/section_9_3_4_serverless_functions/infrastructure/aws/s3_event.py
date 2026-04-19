from rx import of

class MockS3Event:
    """
    THE AWS INFRASTRUCTURE CONTRACT (Reactive S3 Event):
    
    DESIGN NOTE:
    This class simulates the proprietary JSON structure that Amazon S3 sends 
    to a Lambda handler, but wrapped in an Observable stream.
    
    ARCHITECTURAL CRITIQUE:
    In a reactive architecture, we treat cloud triggers as an infinite stream 
    of incoming telemetry. However, notice the deep nesting required to find 
    a simple filename: event['Records'][0]['s3']['object']['key']. 
    This is a textbook "Abstraction Leak." By forcing your business logic 
    to navigate this specific hierarchy, the vendor has effectively "leaked" 
    their internal data model into your application code. Your resizer logic 
    is no longer a pure function; it is a downstream dependent of Amazon's design.
    """
    @staticmethod
    def create_stream(bucket: str, file: str):
        # Emits a proprietary AWS S3 event dictionary
        return of({
            "Records": [{
                "s3": {
                    "bucket": {"name": bucket},
                    "object": {"key": file}
                }
            }]
        })