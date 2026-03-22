# The filename (module) comes after 'from', 
# and the class name comes after 'import'.
from order import Order
from my_db_context import MyDbContext
from smtp_email_service import SmtpEmailService

class OrderController:
    """
    ANTI-PATTERN: THE FAT CONTROLLER.
    A ticking time bomb of mixed concerns.
    """
    def create_order(self, request):
        # 1. Validation Logic
        if not request.items:
            return "Error: Order must have items", 400

        # 2 & 3. Business Logic
        total = sum(i.price * i.qty for i in request.items)
        if request.customer_type == "Gold":
            total *= 0.9

        # 4. Data Access Logic
        db = MyDbContext()
        order = Order()
        order.total = total
        db.orders.add(order)
        db.commit()

        # 5. External Service Logic
        email_svc = SmtpEmailService()
        email_svc.send(request.customer_email, "Confirmed!")

        return {"id": order.id}, 200