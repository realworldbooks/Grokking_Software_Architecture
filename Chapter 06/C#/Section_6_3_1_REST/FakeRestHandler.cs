
using System.Net;

namespace Chapter06.RestExample
{
    // THE FAKE ENDPOINT
    // Intercepts any outbound HTTP call and returns our hardcoded JSON instead.
    public class FakeRestHandler : HttpMessageHandler
    {
        protected override Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
        {
            // The exact JSON from Listing 6.1
            string jsonResponse = @"{
                ""id"": ""123"",
                ""name"": ""Salt & Vinegar Chips"",
                ""price"": 1.50,
                ""calories"": 250,
                ""ingredients"": [ ""Potatoes"", ""Oil"", ""Salt"" ],
                ""manufacturer"": { ""name"": ""SnackCorp"", ""address"": ""123 Food Lane"" }
            }";
            // Package it up as a standard HTTP 200 OK response
            var response = new HttpResponseMessage(HttpStatusCode.OK)
            {
                Content = new StringContent(jsonResponse, System.Text.Encoding.UTF8, "application/json")
            };

            return Task.FromResult(response);
        }
    }
}
