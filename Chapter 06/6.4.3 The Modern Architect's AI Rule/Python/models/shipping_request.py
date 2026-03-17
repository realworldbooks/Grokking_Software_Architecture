from pydantic import BaseModel, Field

class ShippingRequest(BaseModel):
    product_id: str = Field(
        ..., 
        description="The unique ID of the physical product. Do NOT send digital product IDs (like MP3s or eBooks)."
    )
    zip_code: str = Field(
        ..., 
        description="The destination zip code. Must be exactly 5 digits."
    )
