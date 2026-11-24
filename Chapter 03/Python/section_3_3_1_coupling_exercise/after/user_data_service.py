from .user_report_data import UserReportData

"""
THE SOLUTION: The "Chunky" API and High Cohesion.
* ARCHITECTURE NOTE: The service has taken back its rightful responsibility.
Instead of forcing the client to orchestrate four different data fetches, 
the service now performs the work internally.
* This "Chunky" method returns a single DTO. This significantly reduces 
coupling and is a critical optimization for performance in distributed 
systems or microservices.
"""
class UserDataService:
    def get_user_report(self, user_id: int) -> UserReportData:
        print("    [Service] Building chunky report payload internally...")
        
        # In a real app, this method would perform the database queries 
        # and the summation of orders right here.
        return UserReportData(
            name="Jane Doe", 
            email="jane.doe@example.com", 
            total_spent=199.90
        )