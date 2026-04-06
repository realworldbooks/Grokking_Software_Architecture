/**
 * THE FAKE ENDPOINT.
 * Intercepts the HTTP call and returns our precise GraphQL JSON response.
 */
class FakeGraphQLHandler {
    /**
     * Simulates an asynchronous HTTP POST request.
     * @param {string} url - The endpoint being called.
     * @param {string} payload - The GraphQL query wrapped in JSON.
     * @returns {Promise<string>} A promise resolving to the precise JSON response.
     */
    async post(url, payload) {
        // The exact JSON response. Notice there is NO over-fetching here!
        const jsonResponse = `{
          "data": {
            "chipItem": { "name": "Salt & Vinegar Chips" },
            "sodaItem": { "price": 1.50 }
          }
        }`;
        return jsonResponse;
    }
}

module.exports = FakeGraphQLHandler;