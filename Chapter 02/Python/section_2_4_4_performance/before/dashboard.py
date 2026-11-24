from ..database_service import DatabaseService

class Dashboard:
    """
    Represents a dashboard service that fetches data directly from the database.
    ARCHITECTURAL NOTE: This class demonstrates a performance-unaware implementation.
    There is no memory layer protecting the database from repetitive queries.
    """

    def __init__(self):
        self._database_service = DatabaseService()

    def get_dashboard_summary(self, user_id: str) -> dict:
        """
        Gets a summary of dashboard data for a user.
        PROBLEM: Poor Performance due to Expensive, Repetitive Calls.
        1. High Latency: 3 calls * 500ms = 1500ms per request.
        2. High Database Load: Strain on the server from redundant queries.
        3. Not Scalable: The database becomes a bottleneck.
        """
        profile = self._database_service.get_profile(user_id)
        orders = self._database_service.get_orders(user_id)
        activity = self._database_service.get_activity(user_id)

        return {
            "profile": profile,
            "orders": orders,
            "activity": activity
        }