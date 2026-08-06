import { MockBlogDatabase } from "./mockDatabase.js";
import { MonolithBlogEndpoint } from "./endpoint.js";

export class Demo {
  static async run() {
    console.log("\n=== Section 12.6.1: The Monolith Approach (Node.js) ===");
    console.log("THE SETUP: A Blog webpage needs the Post, the Author, and the Comments.");
    console.log("THE MONOLITH: All data lives in ONE database, all code lives in ONE process.\n");

    const db = new MockBlogDatabase();
    const endpoint = new MonolithBlogEndpoint(db);

    console.log("--- SCENARIO 1: Fetch Blog Details (Happy Path) ---");
    console.log("  [ORM] Posts.Include(Author).Include(Comments).FirstOrDefault(id=1)");
    console.log("  [SQL] ONE query. ONE round trip. ZERO network latency\n");

    const start = performance.now();
    try {
      const post = endpoint.getBlogDetails(1);
      const elapsed = ((performance.now() - start) / 1000).toFixed(1);
      console.log("  [Result] Blog Post     : #" + post.id + " " + post.title);
      console.log("  [Result] Author        : " + post.author.name);
      console.log("  [Result] Comments      : " + post.comments.length);
      console.log("  [Latency] " + elapsed + " ms (5 ms single query)\n");
    } catch (ex) { console.log("  [Result] FAILED: " + ex.message + "\n"); }

    console.log("--- SCENARIO 2: The Database is DOWN ---");
    console.log("  [Database] Simulating outage...");
    db.isAvailable = false;
    try { endpoint.getBlogDetails(1); }
    catch (ex) { console.log("  [Result] The whole query fails together."); console.log("  [Result] No partial state. Predictable failure.\n"); }
    finally { db.isAvailable = true; }

    console.log("=".repeat(72));
    console.log("ARCHITECTURAL VERDICT: THE MONOLITH WINS THE SIMPLICITY CONTEST");
    console.log("-".repeat(72));
    console.log("ADVANTAGE #1: ONE QUERY");
    console.log("  - A single SQL JOIN returns the post, author, and comments together.");
    console.log("  - Zero network calls between services. Zero serialization overhead.");
    console.log("  - Execution time: ~5 ms.");
    console.log("\nADVANTAGE #2: PREDICTABLE FAILURE");
    console.log("  - If the database is down, the entire query fails together.");
    console.log("  - There is no such thing as a 'partial page' in a monolith.");
    console.log("\nADVANTAGE #3: SIMPLE TO READ, TEST, AND MAINTAIN");
    console.log("  - No async/await, no HTTP status handling, no fallback strategies.");
    console.log("\nTHE LITMUS TEST: Before breaking this into microservices, ask:");
    console.log("  'Is independent scaling worth the heavy tax in code complexity?'");
    console.log("=".repeat(72) + "\n");
  }
}
