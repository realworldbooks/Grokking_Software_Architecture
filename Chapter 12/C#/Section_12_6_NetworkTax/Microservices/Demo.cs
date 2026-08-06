using System.Diagnostics;

namespace Chapter12.Section_12_6_NetworkTax.Microservices;

public static class Demo
{
    public static async Task RunAsync()
    {
        Console.WriteLine("\n=== Section 12.6.2: The Microservice Aggregator (C#) ===");
        Console.WriteLine("THE SETUP: The SAME Blog webpage, but the team used the Strangler Fig.");
        Console.WriteLine("THE MICROSERVICES: PostService, AuthorService, CommentService -");
        Console.WriteLine("each with its OWN database, spread across the network.\n");

        var postClient = new MockPostServiceClient();
        var authorClient = new MockAuthorServiceClient();
        var commentClient = new MockCommentServiceClient();
        var gateway = new AggregatorGateway(postClient, authorClient, commentClient);

        Console.WriteLine("--- SCENARIO 1: Fetch Blog Details (Happy Path) ---");
        Console.WriteLine("  [Gateway] Firing 3 HTTP calls CONCURRENTLY across the wire:");
        Console.WriteLine("    GET http://post-service/api/posts/1");
        Console.WriteLine("    GET http://author-service/api/authors/1");
        Console.WriteLine("    GET http://comment-service/api/comments/1\n");

        var sw = Stopwatch.StartNew();
        try
        {
            var result = await gateway.GetBlogDetailsAsync(1);
            sw.Stop();
            Console.WriteLine($"  [Result] Blog Post     : #{result.Post["id"]} {result.Post["title"]}");
            Console.WriteLine($"  [Result] Author        : {result.Author["name"]}");
            Console.WriteLine($"  [Result] Comments      : {result.Comments.Count}");
            Console.WriteLine($"  [Latency] {sw.ElapsedMilliseconds} ms (3 x 30ms network calls)\n");
        }
        catch (Exception ex) { Console.WriteLine($"  [Result] FAILED: {ex.Message}\n"); }

        Console.WriteLine("--- SCENARIO 2: The Comment Service is DOWN (Partial Failure) ---");
        Console.WriteLine("  [CommentService] Simulating outage...");
        commentClient.IsAvailable = false;
        try
        {
            var result = await gateway.GetBlogDetailsAsync(1);
            Console.WriteLine($"  [Result] Blog Post     : #{result.Post["id"]} {result.Post["title"]}");
            Console.WriteLine($"  [Result] Author        : {result.Author["name"]}");
            Console.WriteLine($"  [Result] Comments      : {result.Comments.Count} (FALLBACK: served without comments)\n");
        }
        catch (Exception ex) { Console.WriteLine($"  [Result] FAILED: {ex.Message}\n"); }
        finally { commentClient.IsAvailable = true; }

        Console.WriteLine("--- SCENARIO 3: The Post Service is DOWN (Core Failure) ---");
        Console.WriteLine("  [PostService] Simulating outage...");
        postClient.IsAvailable = false;
        try
        {
            await gateway.GetBlogDetailsAsync(1);
        }
        catch (PostServiceUnavailableException ex)
        {
            Console.WriteLine($"  [Result] FAILED: {ex.Message}");
            Console.WriteLine("  [Result] Core post data unavailable - we MUST abort the whole page.\n");
        }
        finally { postClient.IsAvailable = true; }

        Console.WriteLine("ARCHITECTURAL VERDICT: THE MICROSERVICE PAYS THE NETWORK TAX");
        Console.WriteLine("  - 3 separate HTTP calls across the wire (even when concurrent).");
        Console.WriteLine("  - We must manually write fallback logic for partial failures.");
        Console.WriteLine("  - The engineering complexity skyrocketed.\n");
    }
}
