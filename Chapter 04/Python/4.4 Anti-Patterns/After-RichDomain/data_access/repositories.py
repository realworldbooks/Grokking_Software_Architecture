# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/data_access/repositories.py
from .data_access_interfaces import IOrderRepository
# Note: This is a concrete implementation of the IOrderRepository
# interface. It simulates saving an order to a SQL database.
# In a real system, this would contain actual database code
# (e.g., using SQLAlchemy or psycopg2).

class OrderRepository(IOrderRepository):
    """
    Simulates saving an order to a SQL database.
    """
    def save_order(self, order):
        # In a real implementation, you would connect to a DB
        # and execute SQL statements here.
        print("---")
        print("Executing SQL: INSERT INTO Orders (CustomerId, TotalPrice)")
        print(f"VALUES ({order.get_customer().id}, {order.total_price})")
        
        for item_details in order.get_items():
            item = item_details['item']
            quantity = item_details['quantity']
            price = item_details['price']
            print(f"Executing SQL: INSERT INTO OrderItems (OrderId, ItemId, Quantity, Price)")
            print(f"VALUES (LAST_INSERT_ID(), {item.id}, {quantity}, {price})")
        
        print("Order saved to database.")
        print("---")
