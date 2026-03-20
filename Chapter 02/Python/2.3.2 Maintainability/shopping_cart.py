DISCOUNT_RATE = 0.10
TAX_RATE = 0.08

# --- BEFORE REFACTOR ---
def process_order_before(cart_items):
    subtotal = sum(item.price for item in cart_items)
    discount = subtotal * 0.10
    total_after_discount = subtotal - discount
    tax = total_after_discount * 0.08
    final_total = total_after_discount + tax
    return f"Order processed! Your final total is ${final_total:.2f}"

# --- AFTER REFACTOR ---
def calculate_subtotal(items):
    return sum(item.price for item in items)

def apply_discount(amount, rate):
    return amount * (1 - rate)

def add_tax(amount, rate):
    return amount * (1 + rate)

def process_order_after(cart_items):
    subtotal = calculate_subtotal(cart_items)
    total_after_discount = apply_discount(subtotal, DISCOUNT_RATE)
    final_total = add_tax(total_after_discount, TAX_RATE)
    return f"Order processed! Your final total is ${final_total:.2f}"