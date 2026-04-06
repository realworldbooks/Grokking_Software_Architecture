from pydantic import BaseModel, Field
from typing import List

class CartItem(BaseModel):
    product_id: str = Field(
        ..., 
        description="The unique ID of the product. VALID: 'WIDGET-99', 'WIDGET-HEAVY', 'DIGITAL-EBOOK-01'.",
        json_schema_extra={"example": "WIDGET-99"}
    )
    quantity: int = Field(..., gt=0, description="Must be 1 or greater.", json_schema_extra={"example": 1})

class OrderPricingRequest(BaseModel):
    """The payload required to price a user's cart."""
    items: List[CartItem] = Field(
        ..., 
        description="Extract these from the user's conversation history."
    )
    zip_code: str = Field(
        ..., 
        description="AI INSTRUCTION: Must be exactly 5 digits. Strip last 4 if 9-digit provided.",
        json_schema_extra={"example": "12211"}
    )

    # V2 syntax replaces "class Config:"
    model_config = {
        "json_schema_extra": {
            "example": {
                "zip_code": "12211",
                "items": [
                    {"product_id": "WIDGET-99", "quantity": 2},
                    {"product_id": "DIGITAL-EBOOK-01", "quantity": 1}
                ]
            }
        }
    }

class OrderPricingResponse(BaseModel):
    """
    THE RESPONSE DTO.
    AI INSTRUCTION: This contains authoritative totals. Present these exact numbers.
    """
    items_subtotal: float = Field(..., json_schema_extra={"example": 79.96})
    shipping_cost: float = Field(
        ..., 
        description="AI INSTRUCTION: If 0.00, inform user they qualified for free shipping.",
        json_schema_extra={"example": 0.00}
    )
    total_order_cost: float = Field(..., json_schema_extra={"example": 79.96})