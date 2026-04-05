import uvicorn
from fastapi import FastAPI
from .controllers.shipping_controller import router

class Demo:
    """
    The Execution Layer.
    Configures the API and mounts the controllers.
    """
    @staticmethod
    def run() -> None:
        print("--- STARTING AI-DRIVEN API DEMO ---")
        print("Swagger UI will be available at: http://localhost:8000/docs")

        app = FastAPI(
            title="AI Shipping API",
            description="An API designed specifically for AI Agents to calculate shipping costs."
        )

        app.include_router(router)

        # Run the API host
        uvicorn.run(app, host="127.0.0.1", port=8000, log_level="info")