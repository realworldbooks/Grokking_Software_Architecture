"""
This module defines the CartItem class.
"""

class CartItem:
    """
    Represents a single item within a shopping cart.
    
    This class is a simple data structure, often called a data class. 
    Its primary role is to hold data about a cart item, not to contain complex business logic.
    """
    def __init__(self, name, price):
        """
        Initializes a new CartItem.
        
        Args:
            name (str): The name of the product.
            price (float): The price of a single unit of the product.
        """
        self.name = name
        self.price = price