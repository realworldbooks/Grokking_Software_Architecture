from fastapi import FastAPI
from routers import shipping

app = FastAPI(title="AI Shipping API")

# Wire the separated router into the main application
app.include_router(shipping.router, prefix="/api/shipping")
