from .cart_item import CartItem

class ShoppingCart:
    """
    Manages shopping cart operations.
    ARCHITECTURAL NOTE: This "After" class is now highly focused. 
    Because the CartItem model was extracted to its own file, this file 
    only contains the pure business logic and is much easier to read.
    """

    # IMPROVEMENT 1: Use Named Constants
    # Using class-level constants makes the business rules explicit.
    DISCOUNT_RATE = 0.10
    TAX_RATE = 0.08

    def _calculate_subtotal(self, items: list[CartItem]) -> float:
        """Single responsibility: calculating the subtotal."""
        return sum(item.price for item in items)

    def _apply_discount(self, amount: float, rate: float) -> float:
        """Single responsibility: applying discounts."""
        return amount * (1 - rate)

    def _add_tax(self, amount: float, rate: float) -> float:
        """Single responsibility: applying tax rules."""
        return amount * (1 + rate)

    def process_order(self, cart_items: list[CartItem]) -> str:
        """
        Processes the order for a list of cart items.
        IMPROVEMENT 2: Method Decomposition
        This method now reads like a high-level summary of the business process.
        """
        subtotal = self._calculate_subtotal(cart_items)
        total_after_discount = self._apply_discount(subtotal, self.DISCOUNT_RATE)
        final_total = self._add_tax(total_after_discount, self.TAX_RATE)

        return f"Order processed! Your final total is ${final_total:.2f}"