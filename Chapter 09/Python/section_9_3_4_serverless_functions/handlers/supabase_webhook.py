from rx import operators as ops
from ..infrastructure.web.mock_http_request import MockHttpRequest

class SupabaseWebhookHandler:
    """
    CLOUD 3: DATABASE WEBHOOKS (The Reactive Web Standard)
    
    THE ARCHITECTURAL LESSON: 
    Standardization is the ultimate form of decoupling. By wrapping standard 
    HTTP triggers in Observables, we create a pipeline that is 100% portable 
    across the entire Compute Spectrum.
    
    TEACHING NOTE:
    Look at the implementation. There are zero references to 'AWS', 'Azure', 
    or proprietary cloud SDKs. This logic is "Standard-Fluent." It observes 
    a request body stream and projects a result. This represents the pinnacle 
    of architectural maturity because the hosting provider has become an 
    invisible detail. This exact code can run on a Serverless function, a 
    Docker container, or a legacy on-premise server without changing a 
    single operator in the reactive pipeline.
    """
    def handle_stream(self, request: MockHttpRequest):
        return request.get_body_stream().pipe(
            ops.map(lambda payload: self._process_resize(payload))
        )

    def _process_resize(self, payload: dict):
        file_name = payload['record']['name']
        print(f"      [DB Webhook] Reactive stream observed. File: {file_name}")
        print(f"      [DB Webhook] Processing image resize reactively...")
        return f"Webhook processed {file_name} successfully."