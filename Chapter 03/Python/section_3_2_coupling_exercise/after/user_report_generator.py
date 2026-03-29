from .user_data_service import UserDataService

"""
THE SOLUTION: The Loosely Coupled Client.
* ARCHITECTURE NOTE: Look at how much simpler this generator has become. 
It no longer contains a loop, it doesn't perform any math, and it doesn't 
need to know about 'orders'. 
* It simply asks for a 'report' and formats it. This client is now 
"Ignorant by Design"—it only knows about the UserReportData contract, 
not the underlying data source or logic.
"""
class UserReportGenerator:
    def __init__(self):
        self.data_service = UserDataService()

    def generate_report(self, user_id: int) -> str:
        # A single call replaces the "Chatty" sequential calls.
        report = self.data_service.get_user_report(user_id)
        
        return f"User Report for {report.name} ({report.email}) - Total Spent: ${report.total_spent:.2f}"