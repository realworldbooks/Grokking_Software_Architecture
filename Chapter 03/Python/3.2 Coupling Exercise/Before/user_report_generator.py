from user_data_service import UserDataService

"""
ANTI-PATTERN: The Tightly Coupled Client.
* ARCHITECTURE PROBLEM: Because the service is too granular, this generator 
is forced to act as an orchestrator. 
* The generator now knows too much about the internal data structure (User -> 
Orders -> Totals). If the service changes how it stores orders, this 
generator will break. This is a classic violation of Encapsulation.
"""

class UserReportGenerator:
    def __init__(self):
        self.data_service = UserDataService()

    def generate_report(self, user_id):
        # 🚨 ARCHITECTURE WARNING: High temporal coupling. The client must 
        # manage the order of execution for these specific methods.
        name = self.data_service.get_user_name(user_id)
        email = self.data_service.get_user_email(user_id)
        orders = self.data_service.get_user_order_ids(user_id)

        total_spent = 0.0
        
        # 🚨 ARCHITECTURE WARNING: "Chatty" execution.
        # The client is performing business logic (summing totals) that 
        # should belong to the data service.
        for order_id in orders:
            total_spent += self.data_service.get_order_total(order_id)

        return f"User Report for {name} ({email}) - Total Spent: ${total_spent:.2f}"