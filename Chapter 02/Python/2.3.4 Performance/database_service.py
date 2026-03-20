class DatabaseService:
    def get_profile(self, user_id):
        print(f"    [DB] Fetching Profile for {user_id}... (takes 500ms)")
        return "User_Profile_Data"

    def get_orders(self, user_id):
        print(f"    [DB] Fetching Orders for {user_id}... (takes 500ms)")
        return "User_Orders_Data"

    def get_activity(self, user_id):
        print(f"    [DB] Fetching Activity for {user_id}... (takes 500ms)")
        return "User_Activity_Data"