from logger import Logger

class FileLogger(Logger):
    """
    A concrete implementation of the contract.
    """
    def log(self, message: str):
        print(f"(AFTER_LOGGER) File Log: {message}")