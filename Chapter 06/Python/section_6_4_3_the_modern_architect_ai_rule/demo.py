import uvicorn
from fastapi import FastAPI
from .repositories import ProductRepository
from .services import ShippingCalculatorServiceImpl, OrderPricingServiceImpl
from .router import get_order_router

class Demo:
    @staticmethod
    def run():
        print("\n--- STARTING THE MODERN AI ARCHITECT DEMO (PYTHON) ---")
        print("Goal: Turn our Python codebase into a perfect LLM Prompt.")
        print("Swagger UI: http://localhost:8000/docs\n")

        app = FastAPI(
            title="Chapter 06 AI-Ready API",
            description="The API bridge between business logic and LLM Agents."
        )

        repo = ProductRepository()
        shipping = ShippingCalculatorServiceImpl()
        pricing = OrderPricingServiceImpl(shipping, repo)

        app.include_router(get_order_router(pricing))

        uvicorn.run(app, host="127.0.0.1", port=8000)