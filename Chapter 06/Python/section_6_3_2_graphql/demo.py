import json
from .fake_graphql_handler import FakeGraphQLHandler

class Demo:
    """The Execution Layer.
    
    Demonstrates how a GraphQL query prevents over-fetching and reduces network calls.
    """
    
    @staticmethod
    def run() -> None:
        print("--- GRAPHQL PRECISION DEMO ---")
        print("Goal: Get chips name AND soda price in 1 call.")

        # 1. WIRE IT UP
        client = FakeGraphQLHandler()

        # 2. THE REQUEST (The Shopping List)
        # GraphQL sends the query as a JSON payload in a POST request
        query_str = "query { chipItem: product(id: '123') { name } sodaItem: product(id: '456') { price } }"
        payload = json.dumps({"query": query_str})

        url = "https://api.snackcorp.com/graphql"
        print(f"\nCalling: POST {url}")
        raw_result = client.post(url, payload)
        
        # 1. Parse the messy string into a Python dictionary
        parsed_data = json.loads(raw_result)
        
        # 2. Convert it back to a string with an exact 2-space indent
        formatted_result = json.dumps(parsed_data, indent=2)

        # 3. THE VISUAL EVIDENCE
        print("\nResult:")
        print(formatted_result)
        print("\nSuccess: Zero over-fetching!")
        print("We got exactly what we asked for in ONE call.")
