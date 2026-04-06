from abc import ABC, abstractmethod

class ProductRepository(ABC):
    """
    THE DATA ACCESS CONTRACT.
    AI INSTRUCTION: This is the absolute source of truth for the product catalog.
    """
    @abstractmethod
    def get_by_id(self, product_id: str):
        pass

class ShippingCalculatorService(ABC):
    """
    THE SHIPPING MATH CONTRACT.
    AI INSTRUCTION: This service strictly owns all shipping rules.
    """
    @abstractmethod
    def calculate_shipping_cost(self, zip_code: str, total_weight: float, subtotal: float):
        pass

class OrderPricingService(ABC):
    """
    THE SERVICE CONTRACT.
    """
    @abstractmethod
    def calculate_order_totals(self, request):
        pass