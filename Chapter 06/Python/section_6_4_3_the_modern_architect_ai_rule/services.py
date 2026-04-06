from .interfaces import ShippingCalculatorService, OrderPricingService

class ShippingCalculatorServiceImpl(ShippingCalculatorService):
    def calculate_shipping_cost(self, zip_code: str, total_weight: float, subtotal: float):
        if total_weight <= 0 or subtotal > 75.00:
            return 0.00
        
        prefix = zip_code[0] if zip_code else '0'
        base_rate = 3.49
        
        if prefix in "0123":
            cost_per_lb = 0.50
        elif prefix in "89":
            cost_per_lb = 1.50
        else:
            cost_per_lb = 1.00
            
        return round(base_rate + (total_weight * cost_per_lb), 2)

class OrderPricingServiceImpl(OrderPricingService):
    def __init__(self, shipping_service, product_repo):
        self.shipping_service = shipping_service
        self.product_repo = product_repo

    def calculate_order_totals(self, request):
        subtotal = 0.0
        weight = 0.0
        
        for item in request.items:
            product = self.product_repo.get_by_id(item.product_id)
            if not product:
                raise ValueError(f"Product '{item.product_id}' not found.")
            
            subtotal += (product.price * item.quantity)
            if not product.is_digital:
                weight += (product.weight_in_lbs * item.quantity)
        
        shipping = self.shipping_service.calculate_shipping_cost(request.zip_code, weight, subtotal)
        
        return {
            "items_subtotal": round(subtotal, 2),
            "shipping_cost": shipping,
            "total_order_cost": round(subtotal + shipping, 2)
        }