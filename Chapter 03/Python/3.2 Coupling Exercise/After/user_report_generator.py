from user_data_service import UserDataService

class UserReportGenerator:
    def __init__(self):
        self.data_service = UserDataService()

    def generate_report(self, user_id):
        report = self.data_service.get_user_report(user_id)
        return f"User Report for {report.name} ({report.email}) - Total Spent: ${report.total_spent:.2f}"