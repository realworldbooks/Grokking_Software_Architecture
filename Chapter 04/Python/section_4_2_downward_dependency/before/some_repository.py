from .presentation_layer import PresentationLayer

class SomeRepository:
    """
    ARCHITECTURE WARNING: Upward Dependency Violation.
    """
    def __init__(self):
        # 🚨 VIOLATION: Dependency on an upper-layer singleton.
        # This couples our data logic to a specific UI implementation.
        self._ui_layer = PresentationLayer.get_instance()

    def update_data(self, user_id: int, new_data: str):
        print("(Before) Saving data to database...")
        
        # 🚨 VIOLATION: Calling upwards to the UI Layer.
        self._ui_layer.update_status_label(f"(Before) Data {user_id} Saved!")