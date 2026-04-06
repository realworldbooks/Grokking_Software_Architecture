import json

from .fake_rest_handler import FakeRestHandler

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
        raw_result = client.get(url)

        # 1. Parse the messy string into a Python dictionary
        parsed_data = json.loads(raw_result)

        # 2. Convert it back to a string with an exact 2-space indent
        formatted_result = json.dumps(parsed_data, indent=2)
        
        # 3. THE VISUAL EVIDENCE
        print("Result:")
        print(formatted_result)
        print("\nProblem: We got 5 extra fields we didn't ask for (Over-fetching)!")