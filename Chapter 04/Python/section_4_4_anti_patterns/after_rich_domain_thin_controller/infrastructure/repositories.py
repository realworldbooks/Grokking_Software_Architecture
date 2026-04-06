from ..domain.interfaces.data_access_interfaces import IOrderRepository, ICustomerRepository, IItemRepository
from ..domain.models.customer import Customer
from ..domain.models.item import Item
from shared.log_manager import LogManager

class SqlOrderRepository(IOrderRepository):
    """
    ARCHITECTURE NOTE: By isolating SQL logic here, we prevent 
    database concerns from "leaking" into the Presentation or 
    Business layers.
    """
    # Concrete implementation for a SQL database (simulated)
    def get_by_id(self, order_id: int):
        # In a real app, this would perform a SQL query
        LogManager.info("SqlOrderRepository", "(INFRA) SQL: Fetching Order {0}", order_id)
        return None
        
    def save(self, order) -> None:
        LogManager.info("SqlOrderRepository", "(INFRA) SQL: Saving Order {0} with Total {1}", order.id, order.total_price)

class SqlCustomerRepository(ICustomerRepository):
    """Concrete implementation for a SQL database (Simulated)"""
    def get_by_id(self, customer_id: int) -> Customer:
       LogManager.info("SqlCustomerRepository", "(INFRA) SQL: Fetching Customer {0}", customer_id)
        # Returning a dummy Gold customer to test the Rich Domain logic
       return Customer(id=customer_id, type="Gold", email="gold@example.com")
    
class SqlItemRepository(IItemRepository):
    """
    CONCRETE INFRASTRUCTURE IMPLEMENTATION
    ARCHITECTURE NOTE: This is the "Security Guard" of our system. 
    By fetching the Item directly from the database here, we ensure 
    the 'Price' used in calculations is the official one, not a 
    manipulated value sent from a malicious user's JSON request.
    """
    def get_by_id(self, item_id: int) -> Item:
        LogManager.info("SqlItemRepository", "(INFRA) SQL: Fetching official price for Item {0}", item_id)
        
        # Simulated database lookup
        if item_id == 1:
            return Item(id=1, price=100.0, quantity=0)
        elif item_id == 2:
            return Item(id=2, price=50.0, quantity=0)  
        return None