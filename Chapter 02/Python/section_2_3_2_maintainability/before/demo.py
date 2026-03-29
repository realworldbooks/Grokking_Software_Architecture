from .shopping_cart import ShoppingCart, CartItem

class Demo:
    """
    Demonstrates the unoptimized ShoppingCart logic.
    """
    
    @staticmethod
    def run():
        print("--- Maintainability: Shopping Cart (BEFORE) ---")
        print("Notice the 'magic numbers' and the rigid 'God Method' design.\n")
        
        cart = [
            CartItem("Laptop", 1000.00),
            CartItem("Mouse", 50.00)
        ]

        cart_system = ShoppingCart()
        print(cart_system.process_order(cart))
        print("\n-----------------------------------------")