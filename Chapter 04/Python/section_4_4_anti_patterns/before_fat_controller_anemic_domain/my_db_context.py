"""
INFRASTRUCTURE LAYER: DATABASE.
ARCHITECTURE NOTE: This represents a low-level detail 
that the high-level Controller should not be creating.
"""
class MyDbContext:
    def save(self, order):
        print(f"  [DB] Persistence: Saving Order {order.id}")

    @property
    def orders(self):
        # Simulating a collection
        return self

    def add(self, order):
        order.id = 999 # Simulating identity generation
        pass

    def commit(self):
        print("  [DB] Transaction Committed.")