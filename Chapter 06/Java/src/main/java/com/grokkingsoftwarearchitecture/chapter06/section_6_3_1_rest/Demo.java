package restexample.demo;

/**
 * The Execution Layer.
 * Demonstrates the REST over-fetching problem by calling our fake endpoint.
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println("--- REST OVER-FETCHING DEMO ---");
        System.out.println("Goal: We only want the price of the chips.");

        // 1. WIRE IT UP
        FakeRestHandler client = new FakeRestHandler();

        // 2. MAKE THE CALL
        String url = "https://api.snackcorp.com/products/123";
        System.out.println("\nCalling: GET " + url + "\n");

        String result = client.get(url);

        // 3. THE VISUAL EVIDENCE
        System.out.println("Result:");
        System.out.println(result);
        System.out.println("\nProblem: We got 5 extra fields we didn't ask for (Over-fetching)!");
    }
}