"""
ANTI-PATTERN: MODULE-LEVEL STATIC LOGGING.
ARCHITECTURE NOTE: In Python, module-level functions often act 
as global state. While easy to use, they make unit testing 
difficult because they cannot be easily mocked or replaced.
"""
class StaticFileLogger:
    @staticmethod
    def log(message: str):
        print(f"(BEFORE_LOGGER) Static Log: {message}")