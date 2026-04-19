import requests

"""
THE FRAGILE IMPLEMENTATION:

DESIGN NOTE:
This class represents the "Naive" way of handling external dependencies in C#. 
It treats a remote network call as if it were a reliable, local constant.

ARCHITECTURAL CRITIQUE:
1. TEMPORAL COUPLING: This method is "Locked" to the network. Without a timeout 
    or retry policy, the calling thread is held hostage by Zebra's server response time.

2. ABSTRACTION LEAK: There is no Interface (Port). The business logic is forced 
    to depend directly on this concrete implementation and the 'HttpClient' library, 
    violating the Downward Dependency Rule (Chapter 4).

3. THE HAPPY PATH FALLACY: The code assumes EnsureSuccessStatusCode() is a 
    finality. In a distributed system, a 503 (Service Unavailable) is often a 
    temporary "hiccup" that could be solved by a retry. Here, it is a fatal crash.
"""

class FragilePaymentService:
    def charge_credit_card(self, amount: float):
        # This raw function is an exposed liability. 
        # It assumes the network is reliable (The Happy Path Fallacy).
        print(f"      [Payment Service] Attempting to charge ${amount}...")

        # If Zebra is down, this line throws a ConnectionError or Timeout.
        # Since there is no retry logic, the user gets an error and the 
        # business loses money.
        response = requests.post(
            "https://api.zebra.com/charge", 
            json={"amount": amount, "order_id": "12345"},
            timeout=2 # Even with a timeout, failure is absolute here.
        )
        
        response.raise_for_status() 
        return response.json()