import threading

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
        print("Swagger UI: http://localhost:8000/docs")
        print("\n--> Open the URL above and read the descriptions.")
        print("--> Use the prompt below to stop the server and return to the main menu.\n")

        app = FastAPI(
            title="Chapter 06 AI-Ready API",
            description="The API bridge between business logic and LLM Agents."
        )

        repo = ProductRepository()
        shipping = ShippingCalculatorServiceImpl()
        pricing = OrderPricingServiceImpl(shipping, repo)

        app.include_router(get_order_router(pricing))

        # Run the server in a background thread so the menu stays usable.
        config = uvicorn.Config(app, host="127.0.0.1", port=8000, log_level="error")
        server = uvicorn.Server(config)
        thread = threading.Thread(target=server.run, daemon=True)
        thread.start()

        print("\n[SUCCESS] AI-READY API IS RUNNING (PYTHON/FASTAPI)")
        print("Swagger UI available at: http://localhost:8000/docs")
        print("\nPress ENTER to stop the server and return to the main menu...\n")
        input()

        print("Shutting down the FastAPI server...")
        server.should_exit = True
        thread.join()
        print("Server stopped successfully.")