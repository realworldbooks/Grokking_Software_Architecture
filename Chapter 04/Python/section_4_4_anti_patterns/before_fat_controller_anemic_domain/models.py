from pydantic import BaseModel, Field
from typing import List

# --- DTOs (Data Transfer Objects) ---

class OrderItemRequest(BaseModel):
    itemId: int = Field(default=1)
    quantity: int = Field(default=3)

class OrderRequest(BaseModel):
    customerId: int = Field(default=1)
    # This pre-fills the array in the Swagger UI JSON request
    items: List[OrderItemRequest] = Field(default_factory=lambda: [OrderItemRequest()])

# --- ANEMIC DOMAIN MODEL ---

class Order:
    """
    ARCHITECTURAL NOTE: The Anemic Domain Model.
    This class is merely a 'data bag'. It violates encapsulation by 
    exposing its internal state, allowing external classes to bypass 
    business rules.
    """
    def __init__(self):
        self.id = 0
        self.total = 0.0
        self.customer_email = ""

# --- MOCK INFRASTRUCTURE ---

class Customer:
    def __init__(self, customer_id, customer_type, email):
        self.id = customer_id
        self.type = customer_type
        self.email = email

class DbItem:
    def __init__(self, item_id, name, price):
        self.id = item_id
        self.name = name
        self.price = price

class MyDbContext:
    """
    ARCHITECTURAL NOTE: The Mock Database Context.
    This mimics a direct ORM context (like SQLAlchemy), demonstrating 
    how persistence logic is often leaked directly into the API layer.
    """
    def __init__(self):
        self.customers = [Customer(1, "Gold", "a@b.com")]
        self.items = [DbItem(1, "Laptop", 100.0), DbItem(2, "Mouse", 50.0)]
        self.orders = []

    def save_changes(self):
        pass # Simulates a DB commit

class SmtpEmailService:
    def send(self, email, message):
        print(f"Email sent to {email}")