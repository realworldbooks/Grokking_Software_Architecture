from database_service import DatabaseService

class DashboardBefore:
    """
    Represents a dashboard service that fetches data directly from the database.
    This class demonstrates a performance-unaware implementation.
    """
    def __init__(self):
        self.database_service = DatabaseService()

    def get_dashboard_summary(self, user_id):
        """
        Gets a summary of dashboard data for a user.

        Args:
            user_id (str): The ID of the user.

        Returns:
            dict: A dictionary containing the user's dashboard data.
        """
        # PROBLEM: Poor Performance due to Expensive, Repetitive Calls
        # This method fetches all the required data directly from the database
        # every single time it is called. This is slow and not scalable.
        profile = self.database_service.get_profile(user_id)
        orders = self.database_service.get_orders(user_id)
        activity = self.database_service.get_activity(user_id)

        return {
            "profile": profile,
            "orders": orders,
            "activity": activity
        }
