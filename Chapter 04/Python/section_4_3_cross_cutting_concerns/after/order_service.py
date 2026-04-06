from .logger import Logger
from shared.log_manager import LogManager

class OrderService:
    """
    2. THE CLASS "ASKS" FOR THE DEPENDENCY.
    """
    def __init__(self, logger: Logger):
        # The dependency is "injected" via the constructor!
        self.logger = logger

    def save_order(self, order):
        # 3. Use the abstraction (follows DIP)
        self.logger.log("Saving order...") # This uses the injected logger
        LogManager.info("OrderService", "(AFTER_SERVICE) Order saved.") # This uses the shared LogManager