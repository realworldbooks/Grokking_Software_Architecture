"""
ARCHITECTURAL NOTE: The "File Dump" Anti-Pattern
In messy codebases, developers often dump data models (like CartItem) 
into the exact same file as the business logic. As the app grows, 
this file will become thousands of lines long and impossible to navigate.
"""

class CartItem:
    def __init__(self, name: str, price: float):
        self.name = name
        self.price = price

class ShoppingCart:
    """
    Manages shopping cart operations.
    ARCHITECTURAL NOTE: This "Before" class demonstrates poor maintainability 
    due to magic numbers and a lack of Separation of Concerns.
    """
    
    def process_order(self, cart_items: list[CartItem]) -> str:
        # 1. Calculating the subtotal.
        subtotal = 0.0
        for item in cart_items:
            subtotal += item.price

        # PROBLEM 1: "Magic Numbers"
        # The numbers 0.10 and 0.08 are hardcoded values without any explanation.
        discount = subtotal * 0.10
        total_after_discount = subtotal - discount
        
        tax = total_after_discount * 0.08
        final_total = total_after_discount + tax

        # PROBLEM 2: Lack of Separation of Concerns
        # This method does everything: calculates subtotal, applies discount, and adds tax.
        return f"Order processed! Your final total is ${final_total:.2f}"