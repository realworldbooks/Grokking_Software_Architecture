from rx import of
from ...infrastructure.web.mock_webhook_payload import MockWebhookPayload

class MockHttpRequest:
    """
    THE WEB STANDARDS CONTRACT (Standard Reactive HTTP Request):
    
    DESIGN NOTE:
    This class simulates a standard HTTP Request object (like those found in 
    Flask, FastAPI, or Django). It contains the 'MockWebhookPayload'.
    
    ARCHITECTURAL CRITIQUE:
    This is the "Clarity Peak." Because Webhooks interact with your code 
    using the "Language of the Web" (HTTP/JSON) rather than "Vendor SDKs," 
    this infrastructure mock is the most reusable. By wrapping the 
    payload in a standard Request object, we demonstrate that our handler 
    isn't a "Cloud Function"—it's a standard web endpoint that happens 
    to be triggered by a database. This represents the ultimate decoupling 
    on the Compute Spectrum.
    """
    def __init__(self, payload: MockWebhookPayload):
        self._payload = payload
        self.method = "POST"
        self.headers = {
            "Content-Type": "application/json",
            "User-Agent": "Supabase-Webhook-Sim"
        }

    def get_body_stream(self):
        """
        Returns the request body as a Reactive Observable stream.
        
        TEACHING NOTE:
        Modeling the body as an Observable allows our logic to remain 
        asynchronous and projective, consistent with the AWS and Azure 
        implementations, but using standard pipes.
        """
        return self._payload.get_body_stream()