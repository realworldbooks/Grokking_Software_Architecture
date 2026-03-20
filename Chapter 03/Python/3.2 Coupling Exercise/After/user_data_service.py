from user_report_data import UserReportData

class UserDataService:
    def get_user_report(self, user_id):
        print("    [Service] Building chunky report payload internally...")
        return UserReportData(name="Jane Doe", email="jane.doe@example.com", total_spent=199.90)