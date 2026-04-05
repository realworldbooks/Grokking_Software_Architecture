using System;
using System.Net.Http;

namespace Chapter06.RestExample
{
    /// <summary>
    /// The Execution Layer.
    /// Demonstrates the REST over-fetching architectural problem.
    /// </summary>
    public class Demo
    {
        public static void Run()
        {
            Console.WriteLine("--- REST OVER-FETCHING DEMO ---");
            Console.WriteLine("Goal: We only want the price of the chips.");

            // 1. WIRE IT UP
            // We pass our Fake Handler into the standard HttpClient. 
            var fakeHandler = new FakeRestHandler();
            var client = new HttpClient(fakeHandler);

            // 2. MAKE THE CALL
            // We can call ANY fake URL here; the handler intercepts it!
            string url = "https://api.snackcorp.com/products/123";
            Console.WriteLine($"\nCalling: GET {url}\n");
            
            // Keep the strict parameterless Run() contract by resolving the task synchronously
            var result = client.GetStringAsync(url).GetAwaiter().GetResult();

            // 3. THE VISUAL EVIDENCE
            Console.WriteLine("Result:");
            Console.WriteLine(result);
            Console.WriteLine("\nProblem: We got 5 extra fields we didn't ask for (Over-fetching)!");
        }
    }
}