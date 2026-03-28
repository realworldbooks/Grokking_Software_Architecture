import uvicorn
from fastapi import FastAPI
from order_controller import router

# ARCHITECTURAL NOTE: Swagger Configuration.
# FastAPI automatically generates Swagger UI based on these parameters.
# Setting docs_url="/" puts the Swagger UI directly at localhost:5000.
app = FastAPI(
    title="Grokking Software Architecture: The Fat Controller",
    description="Demonstrating the pitfalls of tight coupling and anemic models in Python.",
    version="v1",
    docs_url="/"
)

# Register the Fat Controller routes
app.include_router(router)

if __name__ == "__main__":
    print("--- FAT CONTROLLER APP RUNNING (PYTHON) ---")
    print("Swagger UI available at: http://localhost:5000/")
    print("---------------------------------------------")
    uvicorn.run(app, host="127.0.0.1", port=5000)