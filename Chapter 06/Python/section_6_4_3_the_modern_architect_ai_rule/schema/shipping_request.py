from pydantic import BaseModel, Field

class ShippingRequest(BaseModel):
    """
    THE DTO (The AI's Input Form).
    Extracted from the controller to ensure proper Separation of Concerns.
    """
    product_id: str = Field(
        ..., 
        description="The unique ID of the physical product. Do NOT send digital product IDs (like MP3s or eBooks)."
    )
    zip_code: str = Field(
        ..., 
        description="The destination zip code. Must be exactly 5 digits."
    )