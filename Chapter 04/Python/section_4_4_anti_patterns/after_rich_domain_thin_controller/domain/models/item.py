class Item:
    """
    ARCHITECTURE NOTE: A simple data entity. The behavior regarding 
    how items are priced and discounted is encapsulated inside the 
    Rich 'Order' model, not here.
    """
    def __init__(self, id: int = 0, price: float = 0.0, quantity: int = 0):
        self.id = id
        self.price = price
        self.quantity = quantity