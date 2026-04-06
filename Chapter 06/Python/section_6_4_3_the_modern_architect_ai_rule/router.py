from fastapi import APIRouter, HTTPException
from .schemas import OrderPricingRequest, OrderPricingResponse

def get_order_router(pricing_service):
    router = APIRouter(prefix="/api/OrderPricing", tags=["AI Order Pricing"])

    @router.post(
        "/calculate-totals", 
        response_model=OrderPricingResponse,
        summary="Calculates the total cost of an order",
        description="""
        AI AGENT INSTRUCTIONS:
        Calculates the total cost of an order, including dynamic shipping rates.
        USE THIS ENDPOINT whenever the user asks "How much will my total order cost?"
        CRITICAL: Do NOT attempt to calculate shipping costs or subtotal math yourself.
        """
    )
    async def get_order_totals(request: OrderPricingRequest):
        try:
            return pricing_service.calculate_order_totals(request)
        except ValueError as e:
            raise HTTPException(status_code=404, detail=str(e))
            
    return router