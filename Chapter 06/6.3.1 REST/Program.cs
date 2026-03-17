using System;
using System.Net.Http;
using System.Threading.Tasks;

namespace RestExample.Demo
{
    public class Program
    {
        public static async Task Main()
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
            
            var result = await client.GetStringAsync(url);

            // 3. THE VISUAL EVIDENCE
            Console.WriteLine("Result:");
            Console.WriteLine(result);
            Console.WriteLine("\nProblem: We got 5 extra fields we didn't ask for (Over-fetching)!");
        }
    }
}
