"""
This module demonstrates the concept of maintainability by refactoring a single, 
complex function into smaller, more manageable and reusable functions.
"""

# --- Constants ---

# IMPROVEMENT 1: Use Named Constants
# By defining the discount and tax rates as constants, we give them meaningful names.
# This makes the code self-documenting. If a rate needs to change, we only have to
# update it in one place, reducing the risk of errors.
DISCOUNT_RATE = 0.10
TAX_RATE = 0.08

# --- BEFORE REFACTOR ---

def process_order_before(cart_items):
    """
    Processes a shopping cart order in a single, hard-to-maintain function.
    
    Args:
        cart_items (list[CartItem]): A list of items in the cart.
        
    Returns:
        str: A string summarizing the final total.
    """
    # 1. Calculating the subtotal.
    subtotal = sum(item.price for item in cart_items)
    
    # PROBLEM 1: "Magic Numbers"
    # The numbers 0.10 and 0.08 are "magic numbers." They are hardcoded values
    # without any explanation. If the discount or tax rate changes, a developer
    # has to hunt down these numbers in the code. In a large application, this
    // can be error-prone and time-consuming.
    discount = subtotal * 0.10 # Magic number for discount rate
    total_after_discount = subtotal - discount
    
    tax = total_after_discount * 0.08 # Magic number for tax rate
    final_total = total_after_discount + tax
    
    # PROBLEM 2: Lack of Separation of Concerns
    # This function does everything: calculates subtotal, applies a discount, and adds tax.
    # If the logic for any of these steps changes, we have to modify this entire function.
    # This makes the function rigid and harder to test or reuse individual pieces of logic.
    return f"Order processed! Your final total is ${final_total:.2f}"

# --- AFTER REFACTOR ---

def calculate_subtotal(items):
    """
    Calculates the subtotal of all items in the cart.
    
    Args:
        items (list[CartItem]): A list of cart items.
        
    Returns:
        float: The calculated subtotal.
    """
    # This function now has a single responsibility: calculating the subtotal.
    # It's easy to understand, test, and reuse.
    return sum(item.price for item in items)

def apply_discount(amount, rate):
    """
    Applies a discount to a given amount.
    
    Args:
        amount (float): The original amount.
        rate (float): The discount rate to apply.
        
    Returns:
        float: The amount after the discount is applied.
    """
    # This is another single-responsibility function. If the discount logic changes
    # (e.g., becomes a fixed amount instead of a percentage), we only need to change it here.
    return amount * (1 - rate)

def add_tax(amount, rate):
    """
    Adds tax to a given amount.
    
    Args:
        amount (float): The original amount.
        rate (float): The tax rate to apply.
        
    Returns:
        float: The amount after tax is added.
    """
    # The tax calculation is also isolated. If tax rules change, this is the only
    # place that needs to be updated.
    return amount * (1 + rate)

def process_order_after(cart_items):
    """
    Processes the order using a more maintainable, modular approach.
    
    Args:
        cart_items (list[CartItem]): The list of items in the cart.
        
    Returns:
        str: A string summarizing the final total.
    """
    # IMPROVEMENT 2: Method Decomposition
    # The business logic is now broken down into small, well-named functions.
    # The `process_order_after` function reads like a high-level summary of the steps involved.
    # This makes the code much more readable and easier to follow for new developers.
    # Each smaller function can be tested independently, improving testability.
    subtotal = calculate_subtotal(cart_items)
    total_after_discount = apply_discount(subtotal, DISCOUNT_RATE)
    final_total = add_tax(total_after_discount, TAX_RATE)
    return f"Order processed! Your final total is ${final_total:.2f}"