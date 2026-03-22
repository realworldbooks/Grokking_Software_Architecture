"""
ANTI-PATTERN: The "Chatty" API and Tight Coupling.
* ARCHITECTURE PROBLEM: This service exposes highly granular methods. While 
this might look like it promotes reuse, it forces the client to make multiple 
sequential calls to assemble a complete picture of a User.
* In a distributed system, every method call here represents a potential 
network round-trip. The client is now "leaking" domain knowledge because it 
has to know the exact sequence of calls to calculate a total.
"""

class UserDataService:
    
    def get_user_name(self, user_id):
        # 🚨 ARCHITECTURE WARNING: Fine-grained method increases network overhead.
        print("    [Service] Fetching Name...")
        return "Jane Doe"

    def get_user_email(self, user_id):
        # 🚨 ARCHITECTURE WARNING: Fine-grained method increases network overhead.
        print("    [Service] Fetching Email...")
        return "jane.doe@example.com"

    def get_user_order_ids(self, user_id):
        print("    [Service] Fetching Order IDs...")
        return ["A123", "B456"]

    def get_order_total(self, order_id):
        # 🚨 ARCHITECTURE WARNING: Forcing the client to loop and call this 
        # multiple times creates a "N+1" query problem.
        print(f"    [Service] Fetching Total for Order {order_id}...")
        return 99.95