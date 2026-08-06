"""Section 12.6.1: The Monolith Approach — Simple and Fast (Python).

This scenario demonstrates Listing 12.1: the monolith's blog endpoint.
"""

from time import perf_counter

from .infrastructure.mock_database import DatabaseException, MockBlogDatabase
from .application.endpoint import MonolithBlogEndpoint


class Demo:
    """Runs the Monolith (Listing 12.1) scenario end-to-end."""

    @staticmethod
    def run() -> None:
        print("\n=== Section 12.6.1: The Monolith Approach (Python) ===")
        print("THE SETUP: A Blog webpage needs the Post, the Author, and the Comments.")
        print("THE MONOLITH: All data lives in ONE database, all code lives in ONE process.\n")

        db = MockBlogDatabase()
        endpoint = MonolithBlogEndpoint(db)

        print("--- SCENARIO 1: Fetch Blog Details (Happy Path) ---")
        print("  [ORM] Posts.Include(Author).Include(Comments).FirstOrDefault(id=1)")
        print("  [SQL] ONE query. ONE round trip. ZERO network latency.\n")

        start = perf_counter()
        try:
            view_model = endpoint.get_blog_details(1)
            elapsed_ms = (perf_counter() - start) * 1000.0

            post = view_model.post
            print(f"  [Result] Blog Post     : #{post.id} {post.title}")
            print(f"  [Result] Author        : {post.author.name}")
            print(f"  [Result] Comments      : {len(post.comments)}")
            print(f"  [Latency] {elapsed_ms:.1f} ms (5 ms single query)\n")

        except DatabaseException as ex:
            print(f"  [Result] FAILED: {ex}\n")

        print("--- SCENARIO 2: The Database is DOWN ---")
        print("  [Database] Simulating outage...")
        db.is_available = False
        try:
            endpoint.get_blog_details(1)
        except DatabaseException:
            print("  [Result] The whole query fails together.")
            print("  [Result] No partial state. No half-rendered page. Predictable failure.\n")
        finally:
            db.is_available = True

        print("--- SCENARIO 3: Blog Post NOT Found ---")
        try:
            endpoint.get_blog_details(999)
        except Exception as ex:
            print(f"  [Result] NotFoundException: {ex}\n")

        print("=" * 72)
        print("ARCHITECTURAL VERDICT: THE MONOLITH WINS THE SIMPLICITY CONTEST")
        print("-" * 72)
        print("ADVANTAGE #1: ONE QUERY")
        print("  - A single SQL JOIN returns the post, author, and comments together.")
        print("  - Zero network calls between services. Zero serialization overhead.")
        print("  - Execution time: ~5 ms.")
        print()
        print("ADVANTAGE #2: PREDICTABLE FAILURE")
        print("  - If the database is down, the entire query fails together.")
        print("  - There is no such thing as a 'partial page' in a monolith.")
        print()
        print("ADVANTAGE #3: SIMPLE TO READ, TEST, AND MAINTAIN")
        print("  - No async/await, no HTTP status handling, no fallback strategies.")
        print("  - The business requirement is expressed in a few lines of code.")
        print()
        print("THE LITMUS TEST: Before breaking this into microservices, ask:")
        print("  'Is independent scaling worth the heavy tax in code complexity?'")
        print("=" * 72 + "\n")