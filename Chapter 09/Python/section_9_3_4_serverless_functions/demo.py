from rx import of
from .handlers.aws_lambda import AwsLambdaHandler
from .handlers.supabase_webhook import SupabaseWebhookHandler
from .infrastructure.aws.s3_event import MockS3Event
from .infrastructure.aws.lambda_context import MockLambdaContext
from .infrastructure.web.mock_webhook_payload import MockWebhookPayload
from .infrastructure.web.mock_http_request import MockHttpRequest

class Demo:
    """
    THE REACTIVE MULTI-CLOUD SIMULATOR:
    
    DESIGN NOTE:
    This class acts as the Cloud Runtime (the host environment). In a 
    proper reactive design, the code is "Lazy"—nothing happens until 
    a consumer (the Subscriber) asks for data. 
    
    This Demo simulates three distinct cloud philosophies:
    1. AWS: The Imperative Island (Raw metadata + Manual SDK calls).
    2. Azure: The Declarative App (Platform-managed data injection).
    3. Supabase: The Web Standard (Standard HTTP Webhooks).
    """

    @staticmethod
    def run():
        print("\n=== Section 9.3.4: Serverless Functions (Reactive Vendor Comparison) ===")
        print("THE SETUP: We need to resize an image, but we want to see how different")
        print("           Cloud Providers architect their serverless triggers.\n")

        # ---------------------------------------------------------
        # 1. AWS LAMBDA SIMULATION (Imperative Stream)
        # ---------------------------------------------------------
        print("--- 1. AWS Lambda Simulator ---")
        # We wrap the proprietary AWS JSON structure into an Observable stream
        event_stream_obs = MockS3Event.create_stream("user-images", "rx_aws_lambda.png")
        context = MockLambdaContext()
        
        aws_handler = AwsLambdaHandler()
        aws_handler.handle_stream(event_stream_obs, context).subscribe(
            on_next=lambda res: print(f"  [AWS Result] Emitted: {res}\n"),
            on_completed=lambda: print("      [AWS Infrastructure] Container Destroyed.")
        )

        # ---------------------------------------------------------
        # 2. AZURE FUNCTIONS SIMULATION (Declarative Stream)
        # ---------------------------------------------------------
        print("\n--- 2. Azure Functions Simulator ---")
        # TEACHING NOTE: Azure's 'Declarative' approach means the stream source
        # is the actual file bytes, not a metadata JSON object.
        # (Implementation assumed in azure_function_handler.py)
        print("      [Azure Function] Stream initialized with raw Blob bytes...")
        print("  [Azure Result] Emitted: Azure reactive processed rx_azure.png\n")

        # ---------------------------------------------------------
        # 3. DATABASE WEBHOOK SIMULATION (Standard Web Stream)
        # ---------------------------------------------------------
        print("--- 3. DB Webhook Simulator (Supabase Style) ---")
        # We use a platform-agnostic payload and a standard HTTP Request wrapper
        payload = MockWebhookPayload("objects", "rx_supabase_edge.png")
        request = MockHttpRequest(payload)
        
        web_handler = SupabaseWebhookHandler()
        web_handler.handle_stream(request).subscribe(
            on_next=lambda res: print(f"  [Supabase Result] Emitted: {res}\n"),
            on_completed=lambda: print("      [Supabase Infrastructure] Connection Closed.")
        )

        # ---------------------------------------------------------
        # THE ARCHITECTURAL VERDICT
        # ---------------------------------------------------------
        print("=" * 60)
        print("ARCHITECTURAL VERDICT:")
        print("-" * 60)
        print("AWS: Highest control, but your code is an 'Island' tied to SDKs.")
        print("AZURE: Highest convenience, but the platform 'Owns' your signature.")
        print("SUPABASE: Highest portability, using native Web Standards (Fetch).")
        print("\nREALITY CHECK: Even though the business logic was identical,")
        print("the infrastructure 'Leaked' into all three implementations.")
        print("=" * 60)

if __name__ == "__main__":
    Demo.run_serverless_scenario()