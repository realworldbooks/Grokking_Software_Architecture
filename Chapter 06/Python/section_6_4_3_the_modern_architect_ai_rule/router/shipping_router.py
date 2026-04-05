from fastapi import APIRouter, HTTPException
from ..schema.shipping_request import ShippingRequest

router = APIRouter()

@router.post("/api/shipping/calculate-shipping")
def get_shipping(request: ShippingRequest) -> float:
    """
    Calculates the shipping cost for a specific physical item.
    USE THIS ENDPOINT whenever the user asks 'How much is shipping?'
    Do NOT use this endpoint for digital items.
    """
    if request.product_id.startswith("DIGITAL"):
        raise HTTPException(status_code=400, detail="Digital items do not require shipping.")
    
    return 5.99