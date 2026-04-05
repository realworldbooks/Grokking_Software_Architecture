package graphqlexample.demo;

/**
 * THE FAKE ENDPOINT.
 * Intercepts the HTTP call and returns our precise GraphQL JSON response.
 */
public class FakeGraphQLHandler {
    
    /**
     * Simulates an HTTP POST request to a remote GraphQL server.
     *
     * @param url The mock endpoint being called.
     * @param payload The JSON-encoded GraphQL query.
     * @return A raw JSON string containing exactly what was asked for.
     */
    public String post(String url, String payload) {
        // The exact JSON response. Notice there is NO over-fetching here!
        return "{\n" +
               "  \"data\": {\n" +
               "    \"chipItem\": { \"name\": \"Salt & Vinegar Chips\" },\n" +
               "    \"sodaItem\": { \"price\": 1.50 }\n" +
               "  }\n" +
               "}";
    }
}