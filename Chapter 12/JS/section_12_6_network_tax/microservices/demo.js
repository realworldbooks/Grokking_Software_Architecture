import { AggregatorGateway, PostServiceUnavailableError } from "./aggregator.js";
import { MockPostServiceClient, MockAuthorServiceClient, MockCommentServiceClient } from "./mockClients.js";

export class Demo {
  static async run() {
    console.log("\n=== Section 12.6.2: The Microservice Aggregator (Node.js) ===");
    console.log("THE SETUP: The SAME Blog webpage, but the team used the Strangler Fig.");
    console.log("THE MICROSERVICES: PostService, AuthorService, CommentService -");
    console.log("each with its OWN database, spread across the network.\n");

    const postClient = new MockPostServiceClient(30);
    const authorClient = new MockAuthorServiceClient(30);
    const commentClient = new MockCommentServiceClient(30);
    const gateway = new AggregatorGateway(postClient, authorClient, commentClient);

    console.log("--- SCENARIO 1: Fetch Blog Details (Happy Path) ---");
    console.log("  [Gateway] Firing 3 HTTP calls CONCURRENTLY across the wire:");
    console.log("    GET http://post-service/api/posts/1");
    console.log("    GET http://author-service/api/authors/1");
    console.log("    GET http://comment-service/api/comments/1\n");

    const start = performance.now();
    try {
      const result = await gateway.getBlogDetailsAsync(1);
      const elapsed = ((performance.now() - start) / 1000).toFixed(1);
      console.log("  [Result] Blog Post     : #" + result.post.id + " " + result.post.title);
      console.log("  [Result] Author        : " + result.author.name);
      console.log("  [Result] Comments      : " + result.comments.length);
      console.log("  [Latency] " + elapsed + " ms (3 x 30ms network calls)\n");
    } catch (ex) { console.log("  [Result] FAILED: " + ex.message + "\n"); }

    console.log("--- SCENARIO 2: The Comment Service is DOWN (Partial Failure) ---");
    commentClient.isAvailable = false;
    try {
      const result = await gateway.getBlogDetailsAsync(1);
      console.log("  [Result] Blog Post     : #" + result.post.id + " " + result.post.title);
      console.log("  [Result] Author        : " + result.author.name);
      console.log("  [Result] Comments      : " + result.comments.length + " (FALLBACK: served without comments)\n");
    } catch (ex) { console.log("  [Result] FAILED: " + ex.message + "\n"); }
    finally { commentClient.isAvailable = true; }

    console.log("--- SCENARIO 3: The Post Service is DOWN (Core Failure) ---");
    postClient.isAvailable = false;
    try { await gateway.getBlogDetailsAsync(1); }
    catch (ex) { console.log("  [Result] FAILED: " + ex.message); console.log("  [Result] Core post data unavailable - we MUST abort the whole page.\n"); }
    finally { postClient.isAvailable = true; }

    console.log("=".repeat(72));
    console.log("ARCHITECTURAL VERDICT: THE MICROSERVICE PAYS THE NETWORK TAX");
    console.log("-".repeat(72));
    console.log("TAX #1: THE LATENCY TAX");
    console.log("  - 3 separate HTTP calls across the wire (even when concurrent).");
    console.log("  - The monolith did this in 5 ms; the microservice takes 30+ ms.");
    console.log("\nTAX #2: THE RELIABILITY TAX");
    console.log("  - We must wait for the network, which might drop packets or time out.");
    console.log("\nTAX #3: HANDLING PARTIAL FAILURES");
    console.log("  - We must manually write fallback logic for when one service crashes.");
    console.log("\nTHE LITMUS TEST: The business requirement did NOT change.");
    console.log("The engineering complexity skyrocketed. Is independent scaling worth it?");
    console.log("=".repeat(72) + "\n");
  }
}
