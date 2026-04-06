from datetime import datetime

class LogManager:
    """
    SHARED UTILITY.
    Centralizes logging to ensure consistent formatting across the chapter.
    """
    
    @staticmethod
    def info(context: str, message: str, *args) -> None:
        """
        Logs a formatted message with a timestamp and context.
        
        Args:
            context: The class or module originating the log.
            message: The message string using {0}, {1} style placeholders.
            *args: Variable arguments to fill the placeholders in the message.
        """
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        # Simple string replacement for {0}, {1} to match the Java style
        formatted_message = message
        for i, arg in enumerate(args):
            formatted_message = formatted_message.replace(f"{{{i}}}", str(arg))
        print(f"[{timestamp}] [INFO] [{context}] {formatted_message}")