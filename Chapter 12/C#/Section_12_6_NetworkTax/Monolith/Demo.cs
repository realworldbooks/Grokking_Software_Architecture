using System.Diagnostics;

namespace Chapter12.Section_12_6_NetworkTax.Monolith;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("\n=== Section 12.6.1: The Monolith Approach (C#) ===");
        Console.WriteLine("THE SETUP: A Blog webpage needs the Post, the Author, and the Comments.");
        Console.WriteLine("THE MONOLITH: All data lives in ONE database, all code lives in ONE process.\n");

        var db = new MockBlogDatabase();

        Console.WriteLine("--- SCENARIO 1: Fetch Blog Details (Happy Path) ---");
        Console.WriteLine("  [ORM] Posts.Include(Author).Include(Comments).FirstOrDefault(id=1)");
        Console.WriteLine("  [SQL] ONE query. ONE round trip. ZERO network latency.\n");

        var sw = Stopwatch.StartNew();
        try
        {
            var vm = db.GetBlogDetails(1);
            sw.Stop();
            Console.WriteLine($"  [Result] Blog Post     : #{vm.Id} {vm.Title}");
            Console.WriteLine($"  [Result] Author        : {vm.AuthorName}");
            Console.WriteLine($"  [Result] Comments      : {vm.CommentCount}");
            Console.WriteLine($"  [Latency] {sw.ElapsedMilliseconds} ms (5 ms single query)\n");
        }
        catch (DatabaseException ex) { Console.WriteLine($"  [Result] FAILED: {ex.Message}\n"); }

        Console.WriteLine("--- SCENARIO 2: The Database is DOWN ---");
        Console.WriteLine("  [Database] Simulating outage...");
        db.IsAvailable = false;
        try { db.GetBlogDetails(1); }
        catch (DatabaseException)
        {
            Console.WriteLine("  [Result] The whole query fails together.");
            Console.WriteLine("  [Result] No partial state. No half-rendered page. Predictable failure.\n");
        }
        finally { db.IsAvailable = true; }

        Console.WriteLine("--- SCENARIO 3: Blog Post NOT Found ---");
        try { db.GetBlogDetails(999); }
        catch (KeyNotFoundException)
        {
            Console.WriteLine("  [Result] NotFoundException: Blog post with id 999 was not found.\n");
        }

        Console.WriteLine("ARCHITECTURAL VERDICT: THE MONOLITH WINS THE SIMPLICITY CONTEST");
        Console.WriteLine("  - A single SQL JOIN returns everything. Zero network calls.");
        Console.WriteLine("  - If the database is down, the whole query fails together.");
        Console.WriteLine("  - No async/await, no HTTP status handling, no fallback strategies.\n");
    }
}
