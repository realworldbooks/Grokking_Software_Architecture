from database import Database

class ExportController:
    def __init__(self):
        self.db = Database()

    # Simulating a Flask / FastAPI endpoint
    def export_user_data(self, user_id):
        try:
            user_data = self.db.fetch_user_data(user_id)

            if not user_data:
                print("  [HTTP 404] User not found.")
                return

            # Simple CSV conversion
            headers = "id,name,email\n"
            csv_row = f"{user_data['id']},{user_data['name']},{user_data['email']}\n"
            csv_data = headers + csv_row

            # Simulating HTTP Response
            print("  [HTTP 200] OK")
            print("  [Headers] Content-Type: text/csv")
            print(f"  [Headers] Content-Disposition: attachment; filename=\"user_data_{user_id}.csv\"")
            print("\n--- File Body ---")
            print(csv_data, end="")
            print("-----------------")

        except Exception as e:
            print(f"  [HTTP 500] Export failed: {e}")