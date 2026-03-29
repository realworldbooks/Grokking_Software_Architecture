import threading
import uvicorn
from fastapi import FastAPI
from .order_controller import router

# ARCHITECTURAL NOTE: Swagger Configuration.
# FastAPI automatically generates Swagger UI based on these parameters.
# Setting docs_url="/" puts the Swagger UI directly at localhost:5000.
app = FastAPI(
    title="Grokking Software Architecture: The Fat Controller / Anemic Domain (Anti-Pattern) Demo",
    description="Demonstrating the pitfalls of tight coupling and anemic models in Python.",
    version="v1",
    docs_url="/"
)

# Register the Fat Controller routes
app.include_router(router)

class Demo:
    @staticmethod
    def run():
        print("--- Launching 'The Fat Controller / Anemic Domain' (Anti-Pattern) ---")
        print("Starting the FastAPI Web Server...")

        # Configure the Uvicorn server
        config = uvicorn.Config(app, host="127.0.0.1", port=8000, log_level="error")
        server = uvicorn.Server(config)

        # Run it in a background thread so the terminal doesn't freeze
        thread = threading.Thread(target=server.run)
        thread.start()

        print("\n[SUCCESS] FAT CONTROLLER / ANEMIC DOMAIN APP RUNNING (PYTHON/FASTAPI)")
        print("Swagger UI available at: http://localhost:8000/")
        
        # Wait for the user to test the API in their browser
        input("\nPress ENTER to stop the server and return to the main menu...\n")
        
        # Gracefully shut down the background server
        print("Shutting down the FastAPI server...")
        server.should_exit = True
        thread.join()
        print("Server stopped successfully.")