"""Section 12.6.2: The Microservice Aggregator (Python)."""
import asyncio
from time import perf_counter
from .application.aggregator import AggregatorGateway, PostServiceUnavailableError
from .services.mock_clients import (
    MockAuthorServiceClient, MockCommentServiceClient, MockPostServiceClient,
)

class Demo:
    @staticmethod
    async def run_async() -> None:
        print("\n=== Section 12.6.2: The Microservice Aggregator (Python) ===")
        print("THE SETUP: The SAME Blog webpage, but the team used the Strangler Fig.")
        print("THE MICROSERVICES: PostService, AuthorService, CommentService -")
        print("each with its OWN database, spread across the network.\n")

        post_client = MockPostServiceClient(latency_ms=30)
        author_client = MockAuthorServiceClient(latency_ms=30)
        comment_client = MockCommentServiceClient(latency_ms=30)
        gateway = AggregatorGateway(post_client, author_client, comment_client)

        print("--- SCENARIO 1: Fetch Blog Details (Happy Path) ---")
        print("  [Gateway] Firing 3 HTTP calls CONCURRENTLY across the wire:")
        print("    GET http://post-service/api/posts/1")
        print("    GET http://author-service/api/authors/1")
        print("    GET http://comment-service/api/comments/1\n")

        start = perf_counter()
        try:
            result = await gateway.get_blog_details_async(1)
            elapsed_ms = (perf_counter() - start) * 1000.0
            print(f"  [Result] Blog Post     : #{result.post['id']} {result.post['title']}")
            print(f"  [Result] Author        : {result.author['name']}")
            print(f"  [Result] Comments      : {len(result.comments)}")
            print(f"  [Latency] {elapsed_ms:.1f} ms (3 x 30ms network calls)\n")
        except PostServiceUnavailableError as ex:
            print(f"  [Result] FAILED: {ex}\n")

        print("--- SCENARIO 2: The Comment Service is DOWN (Partial Failure) ---")
        print("  [CommentService] Simulating outage...")
        comment_client.is_available = False
        try:
            result = await gateway.get_blog_details_async(1)
            print(f"  [Result] Blog Post     : #{result.post['id']} {result.post['title']}")
            print(f"  [Result] Author        : {result.author['name']}")
            print(f"  [Result] Comments      : {len(result.comments)} (FALLBACK: served without comments)\n")
        except PostServiceUnavailableError as ex:
            print(f"  [Result] FAILED: {ex}\n")
        finally:
            comment_client.is_available = True

        print("--- SCENARIO 3: The Post Service is DOWN (Core Failure) ---")
        print("  [PostService] Simulating outage...")
        post_client.is_available = False
        try:
            await gateway.get_blog_details_async(1)
        except PostServiceUnavailableError as ex:
            print(f"  [Result] FAILED: {ex}")
            print("  [Result] Core post data unavailable - we MUST abort the whole page.\n")
        finally:
            post_client.is_available = True

        print("=" * 72)
        print("ARCHITECTURAL VERDICT: THE MICROSERVICE PAYS THE NETWORK TAX")
        print("-" * 72)
        print("TAX #1: THE LATENCY TAX")
        print("  - 3 separate HTTP calls across the wire (even when concurrent).")
        print("  - Each call adds serialization, deserialization, and network overhead.")
        print("  - The monolith did this in 5 ms; the microservice takes 30+ ms.")
        print()
        print("TAX #2: THE RELIABILITY TAX")
        print("  - We must wait for the network, which might drop packets or time out.")
        print("  - We introduced async/await and Task.WhenAll just to handle this.")
        print()
        print("TAX #3: HANDLING PARTIAL FAILURES")
        print("  - We must manually write fallback logic for when one service crashes.")
        print("  - The monolith never had to ask 'what if comments are missing?'")
        print()
        print("THE LITMUS TEST: The business requirement did NOT change.")
        print("The engineering complexity skyrocketed. Is independent scaling worth it?")
        print("=" * 72 + "\n")
