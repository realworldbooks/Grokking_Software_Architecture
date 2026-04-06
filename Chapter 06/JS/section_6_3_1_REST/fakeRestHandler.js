/**
 * THE FAKE ENDPOINT.
 * Intercepts outbound HTTP calls to simulate a REST API response.
 * This prevents the demo from relying on a live, external network connection.
 */
class FakeRestHandler {
    /**
     * Simulates an asynchronous HTTP GET request.
     * * @param {string} url - The endpoint being called.
     * @returns {Promise<string>} A promise resolving to the JSON payload.
     */
    async get(url) {
        // The exact JSON payload representing our bloated REST resource
        const jsonResponse = `{
        "id": "123",
        "name": "Salt & Vinegar Chips",
        "price": 1.50,
        "calories": 250,
        "ingredients": [ "Potatoes", "Oil", "Salt" ],
        "manufacturer": { "name": "SnackCorp", "address": "123 Food Lane" }
        }`;
        
        return jsonResponse;
    }
}

module.exports = FakeRestHandler;