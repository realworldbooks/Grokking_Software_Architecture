from rx import of
import json

class MockWebhookPayload:
    """
    THE INFRASTRUCTURE CONTRACT (Standard Webhook Payload):
    
    DESIGN NOTE:
    This represents a platform-agnostic JSON payload sent via a standard HTTP POST. 
    We name this 'MockWebhookPayload' to remain consistent with our AWS and Azure 
    infrastructure mocks. It simulates the data structure sent by modern DBaaS 
    platforms like Supabase.
    
    ARCHITECTURAL CRITIQUE:
    Notice the contrast between this and the AWS S3 mock. This is a "Flat" and 
    "Standard" contract. Because it relies on standard primitive types rather 
    than vendor-specific SDK structures, the 'Contract Coupling' here is minimal. 
    This represents the highest level of architectural stability on the 
    Compute Spectrum because the contract is based on Web Standards, not 
    Vendor Propriety.
    """
    def __init__(self, table: str, file_name: str):
        self.data = {
            "type": "INSERT",
            "table": table,
            "record": {"name": file_name}
        }

    def get_body_stream(self):
        """Returns the payload as a Reactive Observable stream."""
        return of(self.data)