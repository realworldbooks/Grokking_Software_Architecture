import urllib.request
import json

class PortfolioManager:
    """
    THE CORE (Tightly Coupled).
    This class is an architectural liability. It cannot be tested 
    without reaching out into the 'Chaotic Outside World'.
    """

    def calculate_total_value(self, btc_amount: float) -> float:
        """Calculates the total USD value of a Bitcoin balance."""
        
        # VIOLATION 1: Hard-coded network dependency.
        # This ties our business logic directly to the network interface.
        req = urllib.request.Request(
            "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd",
            headers={"User-Agent": "Python App"}
        )

        try:
            with urllib.request.urlopen(req) as response:
                json_data = response.read()
                
                # VIOLATION 2: The logic is tangled with a specific external JSON format.
                price_data = json.loads(json_data)
                current_price = price_data["bitcoin"]["usd"]
                
                return btc_amount * current_price
        except Exception as e:
            # VIOLATION 3: Unhandled infrastructure exceptions bleeding into domain logic.
            raise RuntimeError(f"Infrastructure failure leaked into Core: {e}")