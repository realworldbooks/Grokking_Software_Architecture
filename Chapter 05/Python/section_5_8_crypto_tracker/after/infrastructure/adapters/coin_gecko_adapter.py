import urllib.request
import json
from ...core.ports.price_provider_port import PriceProviderPort

class CoinGeckoAdapter(PriceProviderPort):
    """THE ADAPTER (Production).
    
    Encapsulates messy HTTP calls and 3rd-party JSON shapes.
    This class is the 'Clarity Engineer' for the CoinGecko API.
    """

    def get_bitcoin_price(self) -> float:
        """Fetches the live Bitcoin price from the CoinGecko REST API.
        
        Returns:
            float: The current price per coin.
        """
        url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd"
        req = urllib.request.Request(url, headers={"User-Agent": "Python Architectural Demo"})

        try:
            with urllib.request.urlopen(req) as response:
                json_data = response.read()
                price_data = json.loads(json_data)
                price = float(price_data["bitcoin"]["usd"])
                
                print("(PROD ADAPTER) Successfully fetched live price.")
                return price
        except Exception as e:
            print(f"(PROD ADAPTER) API Failure: {str(e)}")
            raise