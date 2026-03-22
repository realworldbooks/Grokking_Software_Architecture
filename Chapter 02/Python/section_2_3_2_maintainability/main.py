from cart_item import CartItem
import shopping_cart

if __name__ == "__main__":
    cart = [
        CartItem("Laptop", 1000.00),
        CartItem("Mouse", 50.00)
    ]
    
    print("--- Maintainability Example: Shopping Cart Refactor ---")
    print("Before Refactor:")
    print(shopping_cart.process_order_before(cart))
    
    print("\nAfter Refactor:")
    print(shopping_cart.process_order_after(cart))