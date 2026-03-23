class Item:
    """
    ARCHITECTURE NOTE: A simple data entity. The behavior regarding 
    how items are priced and discounted is encapsulated inside the 
    Rich 'Order' model, not here.
    """
    def __init__(self):
        self.price = 0.0
        self.quantity = 0