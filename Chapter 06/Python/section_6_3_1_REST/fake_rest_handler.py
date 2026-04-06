class FakeRestHandler:
    """THE FAKE ENDPOINT.
    
    Intercepts outbound HTTP calls to simulate a REST API response.
    This acts as our 'Airplane Mode' network layer.
    """
    
    def get(self, url: str) -> str:
        """Simulates a synchronous HTTP GET request.
        
        Args:
            url (str): The mock endpoint being called.
            
        Returns:
            str: A raw JSON string representing the full REST resource.
        """
        # The exact JSON payload, showcasing the rigid structure of a REST endpoint
        return '''{
            "id": "123",
            "name": "Salt & Vinegar Chips",
            "price": 1.50,
            "calories": 250,
            "ingredients": [ "Potatoes", "Oil", "Salt" ],
            "manufacturer": { "name": "SnackCorp", "address": "123 Food Lane" }
        }'''