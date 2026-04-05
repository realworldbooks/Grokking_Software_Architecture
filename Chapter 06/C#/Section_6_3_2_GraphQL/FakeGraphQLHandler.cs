using System.Net;

namespace Chapter06.GraphQLExample
{
    // THE FAKE ENDPOINT
    // Intercepts the HTTP call and returns our precise GraphQL JSON response.
    public class FakeGraphQLHandler : HttpMessageHandler
    {
        protected override Task<HttpResponseMessage> SendAsync(
            HttpRequestMessage request,
            CancellationToken cancellationToken)
        {
            // The exact JSON response from Listing 6.2
            // Notice there is NO over-fetching here!
            string jsonResponse =
            @"{
                ""data"": {
                    ""chipItem"": { ""name"": ""Salt & Vinegar Chips"" },
                    ""sodaItem"": { ""price"": 1.50 }
                }
             }";
            // Package it up as a standard HTTP 200 OK response
            var response = new HttpResponseMessage(HttpStatusCode.OK)
            {
                Content = new StringContent(
                    jsonResponse,
                    System.Text.Encoding.UTF8,
                    "application/json")
            };

            return Task.FromResult(response);
        }
    }
}
