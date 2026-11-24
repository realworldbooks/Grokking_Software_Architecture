from .shopping_cart import ShoppingCart
from .cart_item import CartItem

class Demo:
    """
    Demonstrates the optimized ShoppingCart logic.
    """

    @staticmethod
    def run():
        print("--- Maintainability: Shopping Cart (AFTER) ---")
        print("Notice how easy it is to read the decomposed process_order() method.\n")
        
        cart = [
            CartItem("Laptop", 1000.00),
            CartItem("Mouse", 50.00)
        ]

        cart_system = ShoppingCart()
        print(cart_system.process_order(cart))
        print("\n-----------------------------------------")