using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace GraphQLExample.Demo
{
    public class Program
    {
        public static async Task Main()
        {
            Console.WriteLine("--- GRAPHQL PRECISION DEMO ---");
            Console.WriteLine("Goal: Get chips name AND soda price in 1 call.");

            // 1. WIRE IT UP
            var fakeHandler = new FakeGraphQLHandler();
            var client = new HttpClient(fakeHandler);

            // 2. THE REQUEST (The Shopping List)
            // GraphQL sends the query as a JSON payload in a POST request
            string queryStr = "query { chipItem: product(id: '123') { name } " +
                              "sodaItem: product(id: '456') { price } }";
                              
            string payload = $"{{\"query\": \"{queryStr}\"}}";
            var content = new StringContent(
                payload, 
                Encoding.UTF8, 
                "application/json");

            string url = "https://api.snackcorp.com/graphql";
            Console.WriteLine($"\nCalling: POST {url}");
            
            var response = await client.PostAsync(url, content);
            var result = await response.Content.ReadAsStringAsync();

            // 3. THE VISUAL EVIDENCE
            Console.WriteLine("\nResult:");
            Console.WriteLine(result);
            Console.WriteLine("\nSuccess: Zero over-fetching!");
            Console.WriteLine("We got exactly what we asked for in ONE call.");
        }
    }
}
