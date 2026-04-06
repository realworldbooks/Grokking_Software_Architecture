from .logger import Logger
from shared.log_manager import LogManager

class FileLogger(Logger):
    """
    A concrete implementation of the contract.
    """
    def log(self, message: str):
        LogManager.info("FileLogger", "(AFTER_LOGGER) File Log: {0}", message)