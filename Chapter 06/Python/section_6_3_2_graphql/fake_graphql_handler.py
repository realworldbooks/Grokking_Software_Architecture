import textwrap
class FakeGraphQLHandler:
    """THE FAKE ENDPOINT.
    
    Intercepts the HTTP call and returns our precise GraphQL JSON response.
    """
    
    def post(self, url: str, payload: str) -> str:
        """Simulates a synchronous HTTP POST request.
        
        Args:
            url (str): The mock GraphQL endpoint.
            payload (str): The JSON-encoded GraphQL query.
            
        Returns:
            str: A raw JSON string representing the exact requested data.
        """
        # The exact JSON response. Notice there is NO over-fetching here!
        return textwrap.dedent('''{
            "data": {
                "chipItem": { "name": "Salt & Vinegar Chips" },
                "sodaItem": { "price": 1.50 }
            }
        }''')