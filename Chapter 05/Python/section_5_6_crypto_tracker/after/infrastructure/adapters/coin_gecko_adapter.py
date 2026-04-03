import urllib.request
import json
from core.ports.price_provider_port import PriceProviderPort

class CoinGeckoAdapter(PriceProviderPort):
    """
    ADAPTER 2: The Real Production Adapter.
    Encapsulates all the messy HTTP calls and 3rd-party JSON shapes here.
    """

    def get_bitcoin_price(self) -> float:
        req = urllib.request.Request(
            "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd",
            headers={"User-Agent": "Python App"}
        )

        with urllib.request.urlopen(req) as response:
            json_data = response.read()
            price_data = json.loads(json_data)
            return price_data["bitcoin"]["usd"]