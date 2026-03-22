# Chapter 04/Python/4.4 Anti-Patterns/After-RichDomain/data_access/repositories.py
from .data_access_interfaces import IOrderRepository, ICustomerRepository
from ..domain_models.customer import Customer

class SqlOrderRepository(IOrderRepository):
    """
    ARCHITECTURE NOTE: By isolating SQL logic here, we prevent 
    database concerns from "leaking" into the Presentation or 
    Business layers.
    """
    # Concrete implementation for a SQL database (simulated)
    def get_by_id(self, order_id: int):
        return None
        
    def save(self, order):
        pass # SQL Logic

class SqlCustomerRepository(ICustomerRepository):
    def get_by_id(self, customer_id: int):
        customer = Customer()
        customer.id = customer_id
        customer.type = "Gold"
        customer.email = "a@b.com"
        return customer