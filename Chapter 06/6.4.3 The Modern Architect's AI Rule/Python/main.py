from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

# 1. THE API CONFIGURATION
app = FastAPI(
    title="AI Shipping API",
    description="An API designed with AI prompt engineering in mind."
)

# 2. THE DTO (The AI's Input Form)
class ShippingRequest(BaseModel):
    product_id: str = Field(
        ..., 
        description="The unique ID of the physical product. Do NOT send digital product IDs (like MP3s or eBooks)."
    )
    zip_code: str = Field(
        ..., 
        description="The destination zip code. Must be exactly 5 digits."
    )

# 3. THE CONTROLLER (The AI's Tool)
@app.post("/api/shipping/calculate-shipping")
def get_shipping(request: ShippingRequest):
    """
    Calculates the shipping cost for a specific physical item.
    USE THIS ENDPOINT whenever the user asks "How much is shipping?"
    Do NOT use this endpoint for digital items.
    """
    if request.product_id.startswith("DIGITAL"):
        raise HTTPException(status_code=400, detail="Digital items do not require shipping.")
    
    return 5.99
