import threading
import uvicorn
from fastapi import FastAPI, HTTPException
from fastapi.responses import RedirectResponse
from pydantic import BaseModel
from typing import List

from ..infrastructure.repositories import SqlOrderRepository, SqlCustomerRepository, SqlItemRepository
from ..infrastructure.email_service import SmtpEmailService
from ..application.order_service import OrderService
from ..application.order_request import OrderRequest, OrderItemRequest
from .controllers.order_controller import OrderController

# --- FASTAPI SWAGGER DTOs ---
class OrderItemAPIRequest(BaseModel):
    item_id: int
    quantity: int

class OrderAPIRequest(BaseModel):
    customer_id: int
    items: List[OrderItemAPIRequest]
    
    class Config:
        json_schema_extra = {
            "example": {
                "customer_id": 123,
                "items": [
                    {"item_id": 1, "quantity": 1},
                    {"item_id": 2, "quantity": 2}
                ]
            }
        }

# --- FASTAPI SETUP ---
app = FastAPI(
    title="Rich Domain / Thin Controller Traditional 4-Layer Architecture API",
    description="Fat Controller and Anemic Domain eliminated.",
    version="1.0.0"
)

# Redirect the root URL straight to the Swagger docs
@app.get("/", include_in_schema=False)
def redirect_to_swagger():
    return RedirectResponse(url="/docs")

# --- THE COMPOSITION ROOT ---
order_repo = SqlOrderRepository()
customer_repo = SqlCustomerRepository()
item_repo = SqlItemRepository() 
email_service = SmtpEmailService()

order_service = OrderService(
    order_repo=order_repo,
    customer_repo=customer_repo,
    item_repo=item_repo, 
    email_service=email_service
)

# Instantiate your pure-architecture controller
order_controller = OrderController(order_service)

# --- THE WEB ROUTE ---
@app.post("/order")
def create_order(request: OrderAPIRequest):
    # 1. Map the HTTP Request to your internal Application DTOs
    core_items = [OrderItemRequest(item_id=i.item_id, quantity=i.quantity) for i in request.items]
    core_request = OrderRequest(customer_id=request.customer_id, items=core_items)
    
    try:
        # 2. Delegate everything to your framework-agnostic controller
        return order_controller.create_order(core_request)
    except Exception as e:
        # 3. Handle domain errors as HTTP 400 Bad Requests
        raise HTTPException(status_code=400, detail=str(e))

# --- THE DEMO CLASS LAUNCHER ---
class Demo:
    @staticmethod
    def run():
        print("--- Launching Rich Domain / Thin Controller Traditional 4-Layer Architecture (After) ---")
        print("Starting the FastAPI Web Server...")

        # Configure the Uvicorn server
        config = uvicorn.Config(app, host="127.0.0.1", port=8000, log_level="error")
        server = uvicorn.Server(config)

        # Run it in a background thread so the terminal doesn't freeze
        thread = threading.Thread(target=server.run)
        thread.start()

        print("\n[SUCCESS] RICH DOMAIN / THIN CONTROLLER TRADITIONAL 4-LAYER ARCHITECTURE APP RUNNING (PYTHON/FASTAPI)")
        print("Swagger UI available at: http://localhost:8000")
        
        # Wait for the user to test the API in their browser
        input("\nPress ENTER to stop the server and return to the main menu...\n")
        
        # Gracefully shut down the background server
        print("Shutting down the FastAPI server...")
        server.should_exit = True
        thread.join()
        print("Server stopped successfully.")

Demo.run()