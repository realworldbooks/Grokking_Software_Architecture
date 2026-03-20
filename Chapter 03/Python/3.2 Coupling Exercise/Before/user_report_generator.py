from user_data_service import UserDataService

class UserReportGenerator:
    def __init__(self):
        self.data_service = UserDataService()

    def generate_report(self, user_id):
        name = self.data_service.get_user_name(user_id)
        email = self.data_service.get_user_email(user_id)
        orders = self.data_service.get_user_order_ids(user_id)

        total_spent = 0.0
        for order_id in orders:
            total_spent += self.data_service.get_order_total(order_id)

        return f"User Report for {name} ({email}) - Total Spent: ${total_spent:.2f}"