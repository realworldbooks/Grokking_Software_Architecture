from datetime import datetime

class LogManager:
    """
    SHARED UTILITY.
    Centralizes logging to ensure consistent formatting across the chapter.
    """
    
    @staticmethod
    def info(context: str, message: str) -> None:
        """
        Logs a formatted message with a timestamp and context.
        
        Args:
            context: The class or module originating the log.
            message: The message string using {0}, {1} style placeholders.
            *args: Variable arguments to fill the placeholders.
        """
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        print(f"[{timestamp}] [INFO] [{context}] {message}")