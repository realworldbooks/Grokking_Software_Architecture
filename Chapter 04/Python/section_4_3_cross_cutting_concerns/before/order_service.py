from .static_file_logger import StaticFileLogger
from shared.log_manager import LogManager

class OrderService:
    """
    ARCHITECTURE WARNING: This class has a hidden dependency.
    It relies on an external global logger, which prevents 
    it from being a "Pure" business logic component.
    """
    def save_order(self, order):
        # 🚨 VIOLATION: Hidden, rigid dependency.
        StaticFileLogger.log("Saving order...")
        LogManager.info("OrderService", "(BEFORE_SERVICE) Order saved.")