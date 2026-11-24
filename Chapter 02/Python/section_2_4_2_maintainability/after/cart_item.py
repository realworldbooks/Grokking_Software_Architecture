"""
ARCHITECTURAL NOTE: Clean File Separation
By moving our data structures into their own dedicated files, 
we make the codebase vastly easier to navigate. If another developer 
needs to see the shape of a CartItem, they don't have to hunt through 
business logic to find it.
"""

class CartItem:
    def __init__(self, name: str, price: float):
        """
        The price of a single unit of the product.
        """
        self.name = name
        self.price = price