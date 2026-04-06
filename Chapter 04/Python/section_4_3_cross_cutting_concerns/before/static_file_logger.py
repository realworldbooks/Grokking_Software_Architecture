"""
ANTI-PATTERN: MODULE-LEVEL STATIC LOGGING.
ARCHITECTURE NOTE: In Python, module-level functions often act 
as global state. While easy to use, they make unit testing 
difficult because they cannot be easily mocked or replaced.
"""
from shared.log_manager import LogManager
class StaticFileLogger:
    @staticmethod
    def log(message: str):
        LogManager.info("StaticFileLogger", "(BEFORE_LOGGER) Static Log: {0}", message)