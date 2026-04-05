from fake_rest_handler import FakeRestHandler

class Demo:
    """The Execution Layer.
    
    Demonstrates the REST over-fetching architectural problem.
    """
    
    @staticmethod
    def run() -> None:
        print("--- REST OVER-FETCHING DEMO ---")
        print("Goal: We only want the price of the chips.")

        # 1. WIRE IT UP
        client = FakeRestHandler()

        # 2. MAKE THE CALL
        url = "https://api.snackcorp.com/products/123"
        print(f"\nCalling: GET {url}\n")
        
        result = client.get(url)

        # 3. THE VISUAL EVIDENCE
        print("Result:")
        print(result)
        print("\nProblem: We got 5 extra fields we didn't ask for (Over-fetching)!")