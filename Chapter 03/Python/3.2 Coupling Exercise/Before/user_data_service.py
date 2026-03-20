class UserDataService:
    def get_user_name(self, user_id):
        print("    [Service] Fetching Name...")
        return "Jane Doe"

    def get_user_email(self, user_id):
        print("    [Service] Fetching Email...")
        return "jane.doe@example.com"

    def get_user_order_ids(self, user_id):
        print("    [Service] Fetching Order IDs...")
        return ["A123", "B456"]

    def get_order_total(self, order_id):
        print(f"    [Service] Fetching Total for Order {order_id}...")
        return 99.95