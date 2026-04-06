"""
A fake UI layer class to illustrate the bad dependency.
"""
from shared.log_manager import LogManager
class PresentationLayer:
    _instance = None

    @classmethod
    def get_instance(cls):
        if cls._instance is None:
            cls._instance = PresentationLayer()
        return cls._instance

    def update_status_label(self, text: str):
        LogManager.info("PresentationLayer", "[UI UPDATE]: {0}", text)