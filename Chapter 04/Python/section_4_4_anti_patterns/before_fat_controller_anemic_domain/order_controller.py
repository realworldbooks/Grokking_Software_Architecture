import random
from fastapi import APIRouter, HTTPException
from models import OrderRequest, MyDbContext, SmtpEmailService, Order

router = APIRouter()

@router.post("/api/Order")
def create_order(request: OrderRequest):
    """
    ARCHITECTURAL NOTE: The Fat Controller Anti-Pattern.
    This controller produces the EXACT SAME output as the clean architecture, 
    but it does so by violating the Single Responsibility Principle.
    """
    
    # 1. Validation Logic
    if not request.items or len(request.items) == 0:
        raise HTTPException(status_code=400, detail="Order must have items.")

    # 2. Infrastructure Coupling (Using direct instantiation)
    # By directly instantiating MyDbContext, we cannot swap out the database 
    # for testing or future migrations.
    db_context = MyDbContext()

    # Messy Inline Lookup (Controller acting as a repository)
    customer = next((c for c in db_context.customers if c.id == request.customerId), None)
    if not customer:
        raise HTTPException(status_code=400, detail="Customer not found.")

    # 3. Core Business Logic & Leaked Data Access
    total = 0.0
    for req_item in request.items:
        # The controller is doing messy inline database queries instead of 
        # delegating to a dedicated data access layer.
        db_item = next((i for i in db_context.items if i.id == req_item.itemId), None)
        if not db_item:
            raise HTTPException(status_code=400, detail=f"Item {req_item.itemId} not found.")
        
        total += db_item.price * req_item.quantity

    # 4. Hardcoded Business Rules (Applying Discount)
    if customer.type == "Gold":
        total *= 0.9  # 10% discount #A

    # 5. Anemic Model Usage & Persistence
    # We just stuff the calculated data into a dumb property bag.
    order = Order()
    order.id = random.randint(1000, 9999)
    order.total = total
    order.customer_email = customer.email

    db_context.orders.append(order)
    db_context.save_changes()

    # 6. External Service Logic (Hidden Side Effects)
    email_service = SmtpEmailService()
    email_service.send(order.customer_email, "Order Confirmed!")

    # Return exactly matching JSON keys
    return {
        "orderId": order.id,
        "totalPrice": order.total,
        "customerEmail": order.customer_email
    }